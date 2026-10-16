package com.example.data

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class GeminiService {
  private val client = OkHttpClient()

  suspend fun askTechAi(prompt: String): String = withContext(Dispatchers.IO) {
    val apiKey = BuildConfig.GEMINI_API_KEY
    if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
      return@withContext "Hello! I am Tech AI, your coding tutor in Idanre. To use live AI answers, please add your Gemini API key in the Secrets panel in AI Studio! Meanwhile: Python is a great language to start coding because it reads like English. Try typing 'print(\"Hello Idanre\")'!"
    }

    try {
      val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey"
      val jsonBody = JSONObject().apply {
        put("contents", org.json.JSONArray().put(
          JSONObject().put("parts", org.json.JSONArray().put(
            JSONObject().put("text", "You are 'Tech AI', a friendly computer science and coding tutor for secondary school students in Idanre, Ondo State, Nigeria. Teach programming, AI, and computer science in simple English. Question: $prompt")
          ))
        ))
      }

      val body = jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
      val request = Request.Builder().url(url).post(body).build()

      client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) return@withContext "Eeyah! AI service error (${response.code})."
        val responseBody = response.body?.string() ?: return@withContext "No response."
        val json = JSONObject(responseBody)
        val candidates = json.optJSONArray("candidates")
        if (candidates != null && candidates.length() > 0) {
          val content = candidates.getJSONObject(0).optJSONObject("content")
          val parts = content?.optJSONArray("parts")
          if (parts != null && parts.length() > 0) {
            return@withContext parts.getJSONObject(0).optString("text", "No text generated.")
          }
        }
        "No output generated."
      }
    } catch (e: Exception) {
      "Error connecting to AI: ${e.localizedMessage}"
    }
  }

  suspend fun generateSongwritingIdeas(word: String, style: String): String = withContext(Dispatchers.IO) {
    val apiKey = BuildConfig.GEMINI_API_KEY
    if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
      return@withContext "Here are sample songwriting hooks for '$word' ($style):\n1. Walking through Idanre hills with '$word' in my heart.\n2. Cocoa gold and rhythm bold, '$word' is the story told.\n3. Shining bright like morning light, '$word' will make the future bright.\n(Tip: Add your Gemini API key in the Secrets panel for unlimited AI hooks!)"
    }

    try {
      val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey"
      val jsonBody = JSONObject().apply {
        put("contents", org.json.JSONArray().put(
          JSONObject().put("parts", org.json.JSONArray().put(
            JSONObject().put("text", "You are an expert songwriter, poet, and copywriter mentor for youth in Idanre. Write a 4-line catchy song stanza or commercial hook using the word '$word' in a $style style. Use simple, vibrant English.")
          ))
        ))
      }

      val body = jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
      val request = Request.Builder().url(url).post(body).build()

      client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) return@withContext "AI service error (${response.code})."
        val responseBody = response.body?.string() ?: return@withContext "No response."
        val json = JSONObject(responseBody)
        val candidates = json.optJSONArray("candidates")
        if (candidates != null && candidates.length() > 0) {
          val content = candidates.getJSONObject(0).optJSONObject("content")
          val parts = content?.optJSONArray("parts")
          if (parts != null && parts.length() > 0) {
            return@withContext parts.getJSONObject(0).optString("text", "No text generated.")
          }
        }
        "No output generated."
      }
    } catch (e: Exception) {
      "Error: ${e.localizedMessage}"
    }
  }
}
