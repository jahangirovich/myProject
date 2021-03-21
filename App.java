package midtermProject;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Queue;
import java.util.Set;

public class App {
	static Queue<String> queue = new LinkedList<String>();
	static HashSet<String> marked = new HashSet<String>();
	private static final int NUMBER_OF_LINKS = 300;
	private static HashMap<String,HashSet<String>> hashMap = new HashMap<>(); 
	private static HashSet<String> links = new HashSet<>();

	public static void main(String[] args) {

		String s = "https://www.google.com/search?q=ronaldo&num=10";

		queue.add(s);

		marked.add(s);
		Document doc;
		System.out.println("List of url");
		first_cycle: while (!queue.isEmpty()) {

			String v = queue.remove();
			// System.out.println(queue.size());

			// System.out.println(v);

			if(marked.size() == NUMBER_OF_LINKS) break;

			try {
				doc = Jsoup.connect(v)
						.userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
						.get();
				Elements questions = doc.select("a[href]");
				
				hashMap.put(v, links);

				links.clear();

				for (Element link : questions) {

					if (
						(link.attr("abs:href").contains("football") 
							&& 
						(link.attr("abs:href").startsWith("http"))
						)) 
					{
						
						if (marked.size() == NUMBER_OF_LINKS){
							break first_cycle;
						}
						else {
							queue.add(link.attr("abs:href"));
							marked.add(link.attr("abs:href"));
							links.add(link.attr("abs:href"));
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		System.out.println(hashMap.size());
		Set<String> keys = hashMap.keySet();
		for(String key : keys){
			System.out.println(hashMap.get(key).size());
		}
		
		for (String fileName : marked) {
			System.out.println(fileName);
		}
	}

	
}