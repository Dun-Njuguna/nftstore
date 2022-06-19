package com.dKariuki.nftstore.controller

import com.dKariuki.nftstore.exception.NFTNotFoundException
import com.dKariuki.nftstore.model.NFT
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/nft")
class NftStoreController {

    @Value("\${company_name}")
    private lateinit var name: String

    private var NFTs = mutableListOf(
        NFT(1, "CryptoPunks", 100.0),
        NFT(2, "Sneaky Vampire Syndicate", 36.9),
        NFT(3, "The Sevens (Official)", 0.6),
        NFT(4, "Art Blocks Curated", 1.1),
        NFT(5, "Pudgy Penguins", 2.5),
    )

    @GetMapping("/")
    fun getHomePage() = "$name: NFTs Marketplace"

    @GetMapping("/nfts")
    fun getNFTs() = NFTs

    @PostMapping("/addNft")
    @ResponseStatus(HttpStatus.CREATED)
    fun postNFT(@RequestBody nft: NFT): NFT {
        val maxId = NFTs.map { it.id }.maxOrNull() ?: 0
        val nextId = maxId + 1
        val newNft = NFT(id = nextId, name = nft.name, floor_price = nft.floor_price)
        NFTs.add(newNft)
        return newNft
    }

    @GetMapping("/nfts/{id}")
    fun getNFTById(@PathVariable id: Int) : NFT? {
        val nft = NFTs.firstOrNull { it.id == id }
        return nft ?: throw NFTNotFoundException()
    }


}