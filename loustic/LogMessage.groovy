package loustic

import groovy.transform.ToString
import com.google.gson.Gson
import groovy.json.JsonOutput
import loustic.Profile

@ToString
class LogMessage implements Serializable {
	int id
	Profile person
	String msg

	static LogMessage fromJson(String json){
		return new Gson().fromJson(json,LogMessage.class)
	}

	static String toJson(LogMessage msg) {
		return JsonOutput.toJson(msg)
	}

}