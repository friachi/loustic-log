package loustic

import groovy.transform.ToString
import com.google.gson.Gson
import groovy.json.JsonOutput

@ToString
class Profile implements Serializable{
	String email
	String password
	String role

	static Profile fromJson(String json){
		return new Gson().fromJson(json,Profile.class)
	}

	static String toJson(Profile profile) {
		return JsonOutput.toJson(profile)
	}

	static Profile georgesPlaceholderTest(){
		 new Profile(email:'gkhoueiry@murex.com',password:'123456',role:Role.MUSIC_BUFF)
	}
}
