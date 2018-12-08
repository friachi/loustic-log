package loustic

import groovy.transform.ToString
import com.google.gson.Gson
import groovy.json.JsonOutput

@ToString
class Location implements Serializable {
	String host
	String phone
	String email
	String address

	static Location fromJson(String json){
		return new Gson().fromJson(json,Location.class)
	}

	static String toJson(Location Location) {
		return JsonOutput.toJson(Location)
	}

	static Location georgesPlaceholderTest(){
		new Location ( host:'Professor' , phone :'9613169065' , email :'g@khoueiry' , address :'La_Casa')
	}
}