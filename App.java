package midtermProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Queue;

public class App {
	private static File path = new File("C:\\Temp\\WebLinks");
	static Queue<String> queue = new LinkedList<String>();
	static LinkedHashSet<String> marked = new LinkedHashSet<String>();
	private static final int NUMBER_OF_LINKS = 1000;


	public static void main(String[] args) {
		
		String s = "http://web.mit.edu/";

		queue.add(s);

		marked.add(s);
		Document doc;
		System.out.println("List of url");
		OUTER: while (!queue.isEmpty()) {

			String v = queue.remove();
			System.out.println(v);

			if (marked.size() < NUMBER_OF_LINKS) {
				try {
					doc = Jsoup.connect(v).get();
					Elements questions = doc.select("a[href]");
					for (Element link : questions) {
						if ((link.attr("abs:href").contains("mit.edu") && (link
								.attr("abs:href").startsWith("http")))) {
							if (marked.size() == NUMBER_OF_LINKS)
								continue OUTER;
							else {
								queue.add(link.attr("abs:href"));
								marked.add(link.attr("abs:href"));
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		System.out.println("Total Links downloaded :: " + marked.size());

		int counter = 1;
		int listSize = marked.size();
		for (String fileName : marked) {
			System.out.println(fileName);
			counter++;
		}
		System.out.println("Finished downloads");
	}

}