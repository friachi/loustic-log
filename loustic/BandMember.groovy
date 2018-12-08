package loustic

import groovy.transform.ToString
import com.google.gson.Gson
import groovy.json.JsonOutput

@ToString
class BandMember implements Serializable {
	String name = ''
	String role = ''
	String mic = ''
	String micClip = ''
	String phanton = ''
	String dI = ''
	String cable = ''
	String stand = ''
	String recorder = ''

	static BandMember fromJson(String json){
		return new Gson().fromJson(json,BandMember.class)
	}

	static String toJson(BandMember bandm) {
		return JsonOutput.toJson(bandm)
	}

	static BandMember georgesPlaceholderTest(){
		 new BandMember(name : 'Georges KH', role : 'Bystander' , mic : 'MC Doom' , micClip:'no_clip', phanton : 'wtv this is',
		 				 dI : 'dO' , cable : 'youjad' , stand : 'sit_better' , recorder : 'yes')
	}
}