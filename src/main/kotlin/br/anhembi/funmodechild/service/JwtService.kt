package br.anhembi.funmodechild.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.Date

@Service
class JwtService {

    @Value("\${jwt.secret}")
    private val secret: String? = null

    fun extractUsername(token: String): String? {
        val claims = extractAllClaims(token)
        return claims.subject
    }

    fun extractExpiration(token: String): Date {
        val claims = extractAllClaims(token)
        return claims.expiration
    }

    private fun extractAllClaims(token: String): Claims =
        Jwts
            .parserBuilder()
            .setSigningKey(decodeSignKey())
            .build()
            .parseClaimsJws(token)
            .body

    private fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    fun generateToken(username: String): String = createToken(mutableMapOf(), username)

    private fun createToken(claims: Map<String, Any?>, username: String): String {
        val now = LocalDateTime.now()
        val issuedAt: Date = Timestamp.valueOf(now)
        val expiration: Date = Timestamp.valueOf(now.plusMinutes(30))

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(decodeSignKey(), SignatureAlgorithm.HS256).compact()
    }

    private fun decodeSignKey(): Key {
        val keyBytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
