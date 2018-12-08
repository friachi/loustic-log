package loustic

import groovy.transform.ToString
import com.google.gson.Gson
import groovy.json.JsonOutput
import loustic.LogMessage

@ToString
class Song implements Serializable {
	int id = -1
	String title
	String composedBy
	String lyricsBy
	String label
	String publisher
	String lousticReleaseDate // dd/MM/YYYY
	boolean isOriginal // false isCover
	boolean signedContract // false not signed 
	String contractLink
	ArrayList<LogMessage> msgs = []

		static Song fromJson(String json){
		return new Gson().fromJson(json,Song.class)
	}

	static String toJson(Song song) {
		return JsonOutput.toJson(song)
	}

	static Song georgesPlaceholderTest(){
		new Song ( title : 'Layali Operate' , composedBy : 'Bob' , lyricsBy : 'Chris' , label : 'OMX' , publisher : 'Hussein' ,
		 lousticReleaseDate : '12/12/2018' , isOriginal:true , signedContract : false , contractLink : 'http://www.google.com')
	}
}