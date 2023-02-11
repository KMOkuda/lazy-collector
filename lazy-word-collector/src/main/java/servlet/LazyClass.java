package servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Dataset;

/**
 * Servlet implementation class LazyClass
 */
@WebServlet("/LazyClass")
public class LazyClass extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LazyClass() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession ses = request.getSession();

		List<Dataset> datasetList = (List<Dataset>) ses.getAttribute("dataList");
		int getIndex = 0;

		boolean foundContent = false;
		
		System.out.println("getAttribute:" + request.getAttribute("category"));
		if (request.getParameter("category") == null) {
			System.out.println("isEmpty");
			ses.setAttribute("getIndex", 0);
			if(datasetList != null) {
				ses.removeAttribute("dataList");
			}

		} else {

			System.out.println("notNULLNOW.");
			getIndex = (int) ses.getAttribute("getIndex") + 1;
			ses.setAttribute("getIndex", getIndex);

			String kw = request.getParameter("kw");
			int category = Integer.parseInt(request.getParameter("category"));
			int level = Integer.parseInt(request.getParameter("level"));
			
			Dataset dataset = new Dataset(kw, category, level);
			
			if(datasetList == null) {
				datasetList = new ArrayList<>();
			}

			datasetList.add(dataset);
			ses.setAttribute("dataList", datasetList);

		}

		try {
			System.out.println("tried");
			File file = new File("./word-list-test.txt");

			Path p1 = Paths.get("");
			Path p2 = p1.toAbsolutePath();

			System.out.println(p2.toString());
			
			if (file.exists()) {
				System.out.println("aaaaa");
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String content;

				int index = 0;
				String txtkw = null;
				while ((content = br.readLine()) != null) {
					if (index == getIndex) {
						foundContent = true;
						txtkw = content;
						System.out.println("foundContent.");
						System.out.println("index: " + index);
						break;
					}
					index++;
				}
				br.close();

				if (foundContent) {

					Dataset input = new Dataset();
					input.setKw(txtkw);

					request.setAttribute("inputData", input);
				} else {
					try {
						File fileOut = new File("categorization.csv");
						OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileOut.getAbsoluteFile()),
								"SHIFT-JIS");
						BufferedWriter bw = new BufferedWriter(osw);
						Collections.sort(datasetList);
						
						int forIndex = 0;

						for (Dataset data : datasetList) {
							System.out.println(forIndex);
							forIndex++;
							String str = data.getKw() + "," + data.getCategory() + "," + data.getLevel();

							bw.write(str);
							bw.newLine();

						}

						bw.flush();
						bw.close();

					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("notFound");
		}

		ses.setAttribute("dataList", datasetList);
		if (foundContent) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/lazy.jsp");
			dispatcher.forward(request, response);
		} else {
			ses.invalidate();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/done.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession ses = request.getSession();

		
		int getIndex = 0;
		List<Dataset> datasetList = (List<Dataset>) ses.getAttribute("dataList");
		if(ses.getAttribute("getIndex") == null) {
			ses.setAttribute("getIndex", 0);
		}else {
			getIndex = (Integer)(ses.getAttribute("getIndex"));
		}

		boolean foundContent = false;
		
		
		try {
			System.out.println("tried");
			File file = new File("./word-list-test.txt");

			Path p1 = Paths.get("");
			Path p2 = p1.toAbsolutePath();

			System.out.println(p2.toString());
			
			if (file.exists()) {
				System.out.println("aaaaa");
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String content;

				int index = 0;
				String txtkw = null;
				while ((content = br.readLine()) != null) {
					if (index == getIndex) {
						foundContent = true;
						txtkw = content;
						System.out.println("foundContent.");
						System.out.println("index: " + index);
						break;
					}
					index++;
				}
				br.close();

				if (foundContent) {

					Dataset input = new Dataset();
					input.setKw(txtkw);

					request.setAttribute("inputData", input);
				
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("notFound");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/lazy.jsp");
		dispatcher.forward(request, response);
	}

}
