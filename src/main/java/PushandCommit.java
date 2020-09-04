import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;


public class PushandCommit {

	public static void main(String[] args) {
		
		
		/*		echo "# experiment" >> README.md
				git init
				git add README.md
				git commit -m "first commit"
				git remote add origin https://github.com/ZosBHAI/experiment.git
				git push -u origin master
		 */
		
		PushandCommit push = new PushandCommit();
		String localpath="C:\\TechBlog\\JAVA\\localrepo\\--local directory";
		String username= "git username";
		String password="password";
		String remoteRepoName = "github repository name";
		String email="github email id";
		
		push.initLocalRepository(localpath);
		push.createremoteRepo(username, password, email, localpath, remoteRepoName);
		// TODO Auto-generated method stub
		

	}

	
	public boolean initLocalRepository(String localpath)
	{
		
		System.out.println("Step -1: Initializing the local repository" + localpath);
		boolean status = false;
		File dir = null;
		Git git = null;
		try{
			dir = new File(localpath);
			git = Git.init().setDirectory(dir).call();
			status = true;
			}
		
		  catch (IllegalStateException e) {
			System.out.println("IllegalStateException: " + e.getMessage());
			} catch (GitAPIException e) {
				System.out.println("GitAPIException: " + e.getMessage());
			} finally {
				if (git != null) {
					git.getRepository().close();
				}
			}
		return status;
		
		
	
	}
	
	
	
public String createremoteRepo(String username,String password,String email,String localPath,String reponame)
	
	{
		Git git = null;
		String msg = null;
		
		
		try {
		String localrepo = Paths.get(localPath).toString();
		System.out.println(" Local repository " + localrepo);
		File repoDir = new File(localrepo);
		git = Git.open(repoDir);
		System.out.println("Step -2: Staging the files");
		git.add().addFilepattern(".").call();
		
		
		git.commit().setMessage("Created GIT project . Added files from " + localrepo + "").
		setAuthor(username,email).call();
		String remoteRepoURL = "https://github.com/ZosBHAI/" + reponame + ".git";
		URIish uri = new URIish(remoteRepoURL);
		git.remoteAdd().setUri(uri).setName("origin").call();

		System.out.println("Step -3 : Pushing the files to github");
		git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username,password))
				.setRemote("origin").add("master").call();
			msg = "Created Git add  is Complete";
	  
		}
		catch (IOException | InvalidRemoteException | TransportException | URISyntaxException
				| IllegalStateException e){
			
		      System.out.println("Cannot process due to " + e.getMessage());
		}catch (GitAPIException e){
			 System.out.println("Cannot process due to " + e.getMessage());
		}
		finally{
			if (git != null){
				git.getRepository().close();
			}
		}
		return msg;
		}


}
