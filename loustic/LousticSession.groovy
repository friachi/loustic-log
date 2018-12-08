package loustic

import groovy.transform.ToString
import com.google.gson.Gson
import groovy.json.JsonOutput
import loustic.Song
import loustic.BandMember
import loustic.Location

@ToString
class LousticSession implements Serializable {
	int id
	String band
	String manager
	String plannedDate // dd/MM/YYYY French style
	String startTime // dd/MM/YYYY hh:mm:ss
	String endTime // dd/MM/YYYY hh:mm:ss
	ArrayList<BandMember> bandMembers = []
	ArrayList<Song> songs = []
	Map thanks = [:] // [ person : reason ]
	Location location

	static LousticSession fromJson(String json){
		return new Gson().fromJson(json,LousticSession.class)
	}

	static String toJson(LousticSession ls) {
		return JsonOutput.toJson(ls)
	}

	static LousticSession georgesPlaceholderTest(){
		ArrayList<BandMember> bandM = []
		bandM << BandMember.georgesPlaceholderTest()
		ArrayList<Song> sgs = []
		sgs << Song.georgesPlaceholderTest()
		Map tks = ['Mom':'Mjaddra','Abou Karim':'Tonzim']
		new LousticSession(id : -1 , band : 'Jorj' , manager : 'Fahed' , plannedDate : '22/11/2018' , startTime : '' ,
		 bandMembers: bandM, songs:sgs , thanks : tks , location : Location.georgesPlaceholderTest())
	}

}