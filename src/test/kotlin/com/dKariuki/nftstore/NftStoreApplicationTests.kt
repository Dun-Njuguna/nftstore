package com.dKariuki.nftstore

import com.dKariuki.nftstore.model.NFT
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.GreaterThan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class NftStoreApplicationTests(
	@Autowired val mockMvc: MockMvc,
	@Autowired val objectMapper: ObjectMapper
) {

	private val baseRoute = "/api/nft"
	@Test
	fun `Assert NFTs has CryptoPunks as the first item`() {
		mockMvc.get("$baseRoute/nfts")
			.andExpect {
				status { isOk() } // 3
				content { contentType(MediaType.APPLICATION_JSON) }
				jsonPath("$[0].id") { value(1) }
				jsonPath("$[0].name") { value("CryptoPunks") }
				jsonPath("$[0].floor_price") { value(100) }
				jsonPath("$.length()") { GreaterThan(1) }
			}
	}

	@Test
	fun `Assert that we can create an NFT`() {
		mockMvc.get("$baseRoute/nfts/6")
			.andExpect {
				status { isNotFound() }
			}
		val newNFT = NFT(0, "Loot", 45.3)
		mockMvc.post("$baseRoute/addNft") {
			contentType = MediaType.APPLICATION_JSON
			content = objectMapper.writeValueAsString(newNFT)
		}
			.andExpect {
				status { isCreated() }
				content { contentType(MediaType.APPLICATION_JSON) }
				jsonPath("$.name") { value("Loot") }
				jsonPath("$.floor_price") { value(45.3) }
				jsonPath("$.id") { value(6) }
			}
		mockMvc.get("$baseRoute/nfts/6")
			.andExpect {
				status { isOk() }
				content { contentType(MediaType.APPLICATION_JSON) }
				jsonPath("$.name") { value("Loot") }
				jsonPath("$.floor_price") { value(45.3) }
				jsonPath("$.id") { value(6) }
			}
	}

}
