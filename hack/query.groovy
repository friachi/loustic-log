import groovy.sql.Sql

Sql sql = Sql.newInstance( 'jdbc:sqlite:../lsdB.sqlite','org.sqlite.JDBC' )

String filename = args[0]

File queries = new File(filename)

if(!queries.exists()){
	println "$filename not found...Aborting"
	exit 1
}

queries.eachLine { line -> 
	if(!line.startsWith('#') && line){
		if(line.startsWith('select')){
                        println "Executing: $line"
	  		sql.rows(line).each{
	  			println it
	  		}
		}
		else {
			println "Executing: $line"
			sql.execute(line)
		}
	}
}
