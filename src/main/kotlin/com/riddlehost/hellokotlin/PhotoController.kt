package com.riddlehost.hellokotlin

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonJson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URL
import kotlin.reflect.KProperty

class photo(val albumId: Int, var id: Int, var title: String, var url: String, var thumbnailUrl: String)

@RestController
class HelloController {

    @GetMapping("/photo/{Amount}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun run(@PathVariable Amount: Int): String {
        val jsonStr = URL("https://jsonplaceholder.typicode.com/photos").readText();

        val parsedPhotos = Klaxon().parseArray<photo>(jsonStr) ?: return "{\"error\": \"Can't parse json from external service.\"}"

        if(Amount > parsedPhotos.count()) return " {\"error\": \" The requested amount of photos is bigger than the list of photos. \"} "

        val slice = parsedPhotos.slice(0 until Amount);

        return Klaxon().toJsonString(slice)
    }

}