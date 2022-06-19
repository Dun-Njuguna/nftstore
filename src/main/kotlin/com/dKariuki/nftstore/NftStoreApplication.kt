package com.dKariuki.nftstore

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NftStoreApplication

fun main(args: Array<String>) {
	runApplication<NftStoreApplication>(*args)
}
