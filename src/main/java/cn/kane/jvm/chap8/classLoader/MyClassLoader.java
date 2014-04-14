package cn.kane.jvm.chap8.classLoader;

import java.io.File;
import java.io.FilePermission;
import java.io.InputStream;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLStreamHandlerFactory;
import java.net.URLStreamHandler;
import java.security.AccessControlException;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class MyClassLoader extends URLClassLoader {

	protected boolean delegate = false;
	private boolean policy_refresh = false;
	
	private ClassLoader parent = null;
	private ClassLoader system = null;
	private SecurityManager securityManager = null;
	protected String repositories[] = new String[0];
	protected URLStreamHandlerFactory factory = null;
	private ArrayList<Permission> permissionList = new ArrayList<Permission>();
	private HashMap<String,PermissionCollection> loaderPermissionMap = new HashMap<String,PermissionCollection>();
	
	public MyClassLoader() {
		super(new URL[0]);
		this.parent = getParent();
		this.system = getSystemClassLoader();
		securityManager = System.getSecurityManager();
	}

	public MyClassLoader(URLStreamHandlerFactory factory) {
		super(new URL[0], null, factory);
		this.factory = factory;
	}

	public MyClassLoader(ClassLoader parent) {
		super((new URL[0]), parent);
		this.parent = parent;
		this.system = getSystemClassLoader();
		securityManager = System.getSecurityManager();
	}

	public MyClassLoader(ClassLoader parent,URLStreamHandlerFactory factory) {
		super((new URL[0]), parent, factory);
		this.factory = factory;
	}

	public MyClassLoader(String repositories[]) {
		super(convert(repositories));
		this.parent = getParent();
		this.system = getSystemClassLoader();
		securityManager = System.getSecurityManager();
		if (repositories != null) {
			for (int i = 0; i < repositories.length; i++)
				addRepositoryInternal(repositories[i]);
		}
	}

	public MyClassLoader(String repositories[], ClassLoader parent) {
		super(convert(repositories), parent);
		this.parent = parent;
		this.system = getSystemClassLoader();
		securityManager = System.getSecurityManager();
		if (repositories != null) {
			for (int i = 0; i < repositories.length; i++)
				addRepositoryInternal(repositories[i]);
		}
	}

	public MyClassLoader(URL repositories[], ClassLoader parent) {
		super(repositories, parent);
		this.parent = parent;
		this.system = getSystemClassLoader();
		securityManager = System.getSecurityManager();
		if (repositories != null) {
			for (int i = 0; i < repositories.length; i++)
				addRepositoryInternal(repositories[i].toString());
		}
	}

	public boolean getDelegate() {
		return (this.delegate);
	}
	public void setDelegate(boolean delegate) {
		this.delegate = delegate;
	}
	public void setPermissions(String path) {
		if (securityManager != null) {
			permissionList.add(new FilePermission(path + "-", "read"));
		}
	}
	public void setPermissions(URL url) {
		setPermissions(url.toString());
	}
	public String[] findRepositories() {
		return (repositories);
	}
	public void addRepository(String repository) {
		try {
			URLStreamHandler streamHandler = null;
			String protocol = parseProtocol(repository);
			if (factory != null)
				streamHandler = factory.createURLStreamHandler(protocol);
			URL url = new URL(null, repository, streamHandler);
			super.addURL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e.toString());
		}
		addRepositoryInternal(repository);
	}

	// ---------------------------------------------------- ClassLoader Methods

	public Class<?> findClass(String name) throws ClassNotFoundException {
		// (1) Permission to define this class when using a SecurityManager
		if (securityManager != null) {
			int i = name.lastIndexOf('.');
			if (i >= 0) {
				try {
					securityManager.checkPackageDefinition(name.substring(0, i));
				} catch (Exception se) {
					throw new ClassNotFoundException(name);
				}
			}
		}
		// Ask our superclass to locate this class, if possible
		Class<?> clazz = null;
		try {
			synchronized (this) {
				clazz = findLoadedClass(name);
				if (clazz != null)
					return clazz;
				clazz = super.findClass(name);
				if (clazz == null)
					throw new ClassNotFoundException(name);
			}
		} catch (AccessControlException ace) {
			throw new ClassNotFoundException(name);
		} catch (RuntimeException e) {
			throw e;
		}
		return (clazz);
	}

	public URL findResource(String name) {
		URL url = super.findResource(name);
		return (url);
	}

	public Enumeration<URL> findResources(String name) throws IOException {
		return (super.findResources(name));
	}

	public URL getResource(String name) {
		URL url = null;
		// (1) Delegate to parent if requested
		if (delegate) {
			ClassLoader loader = parent;
			if (loader == null)
				loader = system;
			url = loader.getResource(name);
			if (url != null) 
				return (url);
		}
		// (2) Search local repositories
		url = findResource(name);
		if (url != null) 
			return (url);
		// (3) Delegate to parent unconditionally if not already attempted
		if (!delegate) {
			ClassLoader loader = parent;
			if (loader == null)
				loader = system;
			url = loader.getResource(name);
			if (url != null) 
				return (url);
		}
		// (4) Resource was not found
		return (null);
	}

	public InputStream getResourceAsStream(String name) {
		InputStream stream = null;
		// (0) Check for a cached copy of this resource
		stream = findLoadedResource(name);
		if (stream != null) 
			return (stream);
		// (1) Delegate to parent if requested
		if (delegate) {
			ClassLoader loader = parent;
			if (loader == null)
				loader = system;
			stream = loader.getResourceAsStream(name);
			if (stream != null) {
				// FIXME - cache???
				return (stream);
			}
		}
		// (2) Search local repositories
		URL url = findResource(name);
		if (url != null) {
			// FIXME - cache???
			try {
				return (url.openStream());
			} catch (IOException e) {
				return (null);
			}
		}
		// (3) Delegate to parent unconditionally
		if (!delegate) {
			ClassLoader loader = parent;
			if (loader == null)
				loader = system;
			stream = loader.getResourceAsStream(name);
			if (stream != null) {
				// FIXME - cache???
				return (stream);
			}
		}
		// (4) Resource was not found
		return (null);

	}
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return (loadClass(name, false));
	}

	public Class<?> loadClass(String name, boolean resolve)	throws ClassNotFoundException {
		Class<?> clazz = null;
		// (0) Check our previously loaded class cache
		clazz = findLoadedClass(name);
		if (clazz != null) {
			if (resolve)
				resolveClass(clazz);
			return (clazz);
		}
		// If a system class, use system class loader
		if (name.startsWith("java.")) {
			ClassLoader loader = system;
			clazz = loader.loadClass(name);
			if (clazz != null) {
				if (resolve)
					resolveClass(clazz);
				return (clazz);
			}
			throw new ClassNotFoundException(name);
		}
		// (.5) Permission to access this class when using a SecurityManager
		if (securityManager != null) {
			int i = name.lastIndexOf('.');
			if (i >= 0) {
				try {
					securityManager.checkPackageAccess(name.substring(0, i));
				} catch (SecurityException se) {
					String error = "Security Violation, attempt to use " + "Restricted Class: " + name;
					System.out.println(error);
					se.printStackTrace();
					throw new ClassNotFoundException(error);
				}
			}
		}
		// (1) Delegate to our parent if requested
		if (delegate) {
			ClassLoader loader = parent;
			if (loader == null)
				loader = system;
			try {
				clazz = loader.loadClass(name);
				if (clazz != null) {
					if (resolve)
						resolveClass(clazz);
					return (clazz);
				}
			} catch (ClassNotFoundException e) {
				;
			}
		}
		// (2) Search local repositories
		try {
			clazz = findClass(name);
			if (clazz != null) {
				if (resolve)
					resolveClass(clazz);
				return (clazz);
			}
		} catch (ClassNotFoundException e) {
			;
		}
		// (3) Delegate to parent unconditionally
		if (!delegate) {
			ClassLoader loader = parent;
			if (loader == null)
				loader = system;
			try {
				clazz = loader.loadClass(name);
				if (clazz != null) {
					if (resolve)
						resolveClass(clazz);
					return (clazz);
				}
			} catch (ClassNotFoundException e) {
				;
			}
		}
		// This class was not found
		throw new ClassNotFoundException(name);

	}

	protected final PermissionCollection getPermissions(CodeSource codeSource) {
		if (!policy_refresh) {
			Policy policy = Policy.getPolicy();
			policy.refresh();
			policy_refresh = true;
		}
		String codeUrl = codeSource.getLocation().toString();
		PermissionCollection pc;
		if ((pc = (PermissionCollection) loaderPermissionMap.get(codeUrl)) == null) {
			pc = super.getPermissions(codeSource);
			if (pc != null) {
				Iterator<Permission> perms = permissionList.iterator();
				while (perms.hasNext()) {
					Permission p = (Permission) perms.next();
					pc.add(p);
				}
				loaderPermissionMap.put(codeUrl, pc);
			}
		}
		return (pc);
	}
	protected static String parseProtocol(String spec) {
		if (spec == null)
			return "";
		int pos = spec.indexOf(':');
		if (pos <= 0)
			return "";
		return spec.substring(0, pos).trim();
	}
	protected void addRepositoryInternal(String repository) {
		URLStreamHandler streamHandler = null;
		String protocol = parseProtocol(repository);
		if (factory != null)
			streamHandler = factory.createURLStreamHandler(protocol);
		// Validate the manifest of a JAR file repository
		if (!repository.endsWith(File.separator) && !repository.endsWith("/")) {
			JarFile jarFile = null;
			try {
				Manifest manifest = null;
				if (repository.startsWith("jar:")) {
					URL url = new URL(null, repository, streamHandler);
					JarURLConnection conn = (JarURLConnection) url.openConnection();
					conn.setAllowUserInteraction(false);
					conn.setDoInput(true);
					conn.setDoOutput(false);
					conn.connect();
					jarFile = conn.getJarFile();
				} else if (repository.startsWith("file://")) {
					jarFile = new JarFile(repository.substring(7));
				} else if (repository.startsWith("file:")) {
					jarFile = new JarFile(repository.substring(5));
				} else if (repository.endsWith(".jar")) {
					URL url = new URL(null, repository, streamHandler);
					URLConnection conn = url.openConnection();
					JarInputStream jis = new JarInputStream(conn.getInputStream());
					manifest = jis.getManifest();
				} else {
					throw new IllegalArgumentException("addRepositoryInternal:  Invalid URL '" + repository + "'");
				}
				if (!((manifest == null) && (jarFile == null))) {
					if ((manifest == null) && (jarFile != null))
						manifest = jarFile.getManifest();
				}
			} catch (Throwable t) {
				t.printStackTrace();
				throw new IllegalArgumentException("addRepositoryInternal: " + t);
			} finally {
				if (jarFile != null) {
					try {
						jarFile.close();
					} catch (Throwable t) {
					}
				}
			}
		}
		// Add this repository to our internal list
		synchronized (repositories) {
			String results[] = new String[repositories.length + 1];
			System.arraycopy(repositories, 0, results, 0, repositories.length);
			results[repositories.length] = repository;
			repositories = results;
		}

	}
	protected static URL[] convert(String input[]) {
		return convert(input, null);
	}
	protected static URL[] convert(String input[], URLStreamHandlerFactory factory) {
		URLStreamHandler streamHandler = null;
		URL url[] = new URL[input.length];
		for (int i = 0; i < url.length; i++) {
			try {
				String protocol = parseProtocol(input[i]);
				if (factory != null)
					streamHandler = factory.createURLStreamHandler(protocol);
				else
					streamHandler = null;
				url[i] = new URL(null, input[i], streamHandler);
			} catch (MalformedURLException e) {
				url[i] = null;
			}
		}
		return (url);
	}

	protected InputStream findLoadedResource(String name) {
		return (null); // FIXME - findLoadedResource()
	}

}
