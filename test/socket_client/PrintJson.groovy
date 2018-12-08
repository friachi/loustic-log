@Grapes([
        @Grab('io.ratpack:ratpack-groovy:1.5.0'),
        @Grab('org.slf4j:slf4j-simple:1.7.25'),
        @Grab(group='org.xerial', module='sqlite-jdbc', version='3.23.1'),
        @Grab(group='log4j', module='log4j', version='1.2.17'),
        @Grab(group='com.google.code.gson', module='gson', version='2.8.5')
])

package test.socket_client

import loustic.Profile
import loustic.LousticSession
import loustic.LogMessage
import loustic.Song
import loustic.BandMember

println Profile.toJson(Profile.georgesPlaceholderTest())
println BandMember.toJson(BandMember.georgesPlaceholderTest())
println LousticSession.toJson(LousticSession.georgesPlaceholderTest())
println Song.toJson(Song.georgesPlaceholderTest())
