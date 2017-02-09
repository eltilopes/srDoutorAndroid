package br.com.srdoutorandroid.components;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {

	// constructor
	public XMLParser() {

	}

	/**
	 * Getting XML from URL making HTTP request
	 * @param url string
	 * */
	public String getXmlFromUrl(String url) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<music>\n" +
				"    <song>\n" +
				"        <id>1</id>\n" +
				"        <title>Dr. Ana Maria</title>\n" +
				"        <artist>Fisioterapeuta</artist>\n" +
				"        <duration>4:47</duration>\n" +
				"        <plays>1662</plays>\n" +
				"        <thumb_url>http://t1.uccdn.com/pt/images/6/8/0/img_como_fazer_dieta_com_a_ajuda_dos_medicos_1086_600.jpg</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>2</id>\n" +
				"        <title>Dr. Rodolfo Bruno</title>\n" +
				"        <artist>Cardiologista</artist>\n" +
				"        <duration>4:38</duration>\n" +
				"        <plays>1900</plays>\n" +
				"        <thumb_url>http://www.medical-pe.com.br/wp-content/uploads/2013/09/img-interna-esps-como-sera-medico-futuro.jpg</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>2</id>\n" +
				"        <title>Dra. Alice Moraes</title>\n" +
				"        <artist>Ginecologista</artist>\n" +
				"        <duration>4:38</duration>\n" +
				"        <plays>1900</plays>\n" +
				"        <thumb_url>http://www.unimedjf.coop.br/novosite/Imagens/Site/imagemDestaqueDownloadGuiaMedico.png</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>2</id>\n" +
				"        <title>Dra. Fabio Pinto</title>\n" +
				"        <artist>Terapeuta</artist>\n" +
				"        <duration>4:38</duration>\n" +
				"        <plays>1900</plays>\n" +
				"        <thumb_url>http://www.unimedjf.coop.br/novosite/Imagens/Site/imagemDestaqueDownloadGuiaMedico.png</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>1</id>\n" +
				"        <title>Dr. Ana Maria</title>\n" +
				"        <artist>Fisioterapeuta</artist>\n" +
				"        <duration>4:47</duration>\n" +
				"        <plays>1662</plays>\n" +
				"        <thumb_url>http://t1.uccdn.com/pt/images/6/8/0/img_como_fazer_dieta_com_a_ajuda_dos_medicos_1086_600.jpg</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>2</id>\n" +
				"        <title>Dr. Rodolfo Bruno</title>\n" +
				"        <artist>Cardiologista</artist>\n" +
				"        <duration>4:38</duration>\n" +
				"        <plays>1900</plays>\n" +
				"        <thumb_url>http://www.medical-pe.com.br/wp-content/uploads/2013/09/img-interna-esps-como-sera-medico-futuro.jpg</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>2</id>\n" +
				"        <title>Dra. Alice Moraes</title>\n" +
				"        <artist>Ginecologista</artist>\n" +
				"        <duration>4:38</duration>\n" +
				"        <plays>1900</plays>\n" +
				"        <thumb_url>http://www.unimedjf.coop.br/novosite/Imagens/Site/imagemDestaqueDownloadGuiaMedico.png</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>2</id>\n" +
				"        <title>Dra. Fabio Pinto</title>\n" +
				"        <artist>Terapeuta</artist>\n" +
				"        <duration>4:38</duration>\n" +
				"        <plays>1900</plays>\n" +
				"        <thumb_url>http://www.unimedjf.coop.br/novosite/Imagens/Site/imagemDestaqueDownloadGuiaMedico.png</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>1</id>\n" +
				"        <title>Dr. Ana Maria</title>\n" +
				"        <artist>Fisioterapeuta</artist>\n" +
				"        <duration>4:47</duration>\n" +
				"        <plays>1662</plays>\n" +
				"        <thumb_url>http://t1.uccdn.com/pt/images/6/8/0/img_como_fazer_dieta_com_a_ajuda_dos_medicos_1086_600.jpg</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>2</id>\n" +
				"        <title>Dr. Rodolfo Bruno</title>\n" +
				"        <artist>Cardiologista</artist>\n" +
				"        <duration>4:38</duration>\n" +
				"        <plays>1900</plays>\n" +
				"        <thumb_url>http://www.medical-pe.com.br/wp-content/uploads/2013/09/img-interna-esps-como-sera-medico-futuro.jpg</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>2</id>\n" +
				"        <title>Dra. Alice Moraes</title>\n" +
				"        <artist>Ginecologista</artist>\n" +
				"        <duration>4:38</duration>\n" +
				"        <plays>1900</plays>\n" +
				"        <thumb_url>http://www.unimedjf.coop.br/novosite/Imagens/Site/imagemDestaqueDownloadGuiaMedico.png</thumb_url>\n" +
				"    </song>\n" +
				"    <song>\n" +
				"        <id>2</id>\n" +
				"        <title>Dra. Fabio Pinto</title>\n" +
				"        <artist>Terapeuta</artist>\n" +
				"        <duration>4:38</duration>\n" +
				"        <plays>1900</plays>\n" +
				"        <thumb_url>http://www.unimedjf.coop.br/novosite/Imagens/Site/imagemDestaqueDownloadGuiaMedico.png</thumb_url>\n" +
				"    </song>\n" +
				"</music>";
	}

	/**
	 * Getting XML DOM element
	 * @param XML string
	 * */
	public Document getDomElement(String xml){
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		} catch (ParserConfigurationException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		}

		return doc;
	}

	/** Getting node value
	 * @param elem element
	 */
	public final String getElementValue( Node elem ) {
		Node child;
		if( elem != null){
			if (elem.hasChildNodes()){
				for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
					if( child.getNodeType() == Node.TEXT_NODE  ){
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	/**
	 * Getting node value
	 * @param Element node
	 * @param key string
	 * */
	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}
}
