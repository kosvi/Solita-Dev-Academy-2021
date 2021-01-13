package launch;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main {
	public static void main(String[] args) throws Exception {
		String webappDirLocation = new File("src/main/webapp").getAbsolutePath();
		Tomcat tomcat = new Tomcat();
		// I am going to hardcode port since this is going to run in Docker anyways
		tomcat.setPort(8080);

		tomcat.getConnector();
		Context webApp = tomcat.addWebapp("/", webappDirLocation);

		// makes developing easier
		webApp.setReloadable(true);

		WebResourceRoot resources = new StandardRoot(webApp);
		resources.addPreResources(
				new DirResourceSet(resources, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/"));
		webApp.setResources(resources);

		webApp.setRequestCharacterEncoding("utf-8");
		webApp.setResponseCharacterEncoding("utf-8");

		tomcat.start();
		tomcat.getServer().await();
	}
}
