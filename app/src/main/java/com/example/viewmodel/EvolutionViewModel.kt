package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.DatamuseClient
import com.example.data.DatamuseWord
import com.example.data.GeminiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatMessage(
  val id: String = java.util.UUID.randomUUID().toString(),
  val text: String,
  val isUser: Boolean,
  val timestamp: Long = System.currentTimeMillis()
)

data class CodeSnippet(
  val title: String,
  val category: String,
  val code: String,
  val explanation: String
)

enum class SearchTab {
  RHYMES, NEAR_RHYMES, SYNONYMS, ANTONYMS, AI_SONGWRITER
}

class EvolutionViewModel : ViewModel() {
  private val geminiService = GeminiService()

  // Search state
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedTab = MutableStateFlow(SearchTab.RHYMES)
  val selectedTab: StateFlow<SearchTab> = _selectedTab.asStateFlow()

  private val _searchResults = MutableStateFlow<List<DatamuseWord>>(emptyList())
  val searchResults: StateFlow<List<DatamuseWord>> = _searchResults.asStateFlow()

  private val _aiSongwritingResult = MutableStateFlow("")
  val aiSongwritingResult: StateFlow<String> = _aiSongwritingResult.asStateFlow()

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

  private val _errorMessage = MutableStateFlow<String?>(null)
  val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

  // Article Summarizer State
  private val _articleInput = MutableStateFlow("")
  val articleInput: StateFlow<String> = _articleInput.asStateFlow()

  private val _summaryResult = MutableStateFlow("")
  val summaryResult: StateFlow<String> = _summaryResult.asStateFlow()

  private val _isSummarizing = MutableStateFlow(false)
  val isSummarizing: StateFlow<Boolean> = _isSummarizing.asStateFlow()

  // Gratitude Note Sender State
  private val _gratitudeMessage = MutableStateFlow("")
  val gratitudeMessage: StateFlow<String> = _gratitudeMessage.asStateFlow()

  private val _isGeneratingGratitude = MutableStateFlow(false)
  val isGeneratingGratitude: StateFlow<Boolean> = _isGeneratingGratitude.asStateFlow()

  // Icebreaker Generator State
  private val _currentIcebreaker = MutableStateFlow("If you could instantly master any skill without practicing, what would you choose and why?")
  val currentIcebreaker: StateFlow<String> = _currentIcebreaker.asStateFlow()

  private val _isGeneratingIcebreaker = MutableStateFlow(false)
  val isGeneratingIcebreaker: StateFlow<Boolean> = _isGeneratingIcebreaker.asStateFlow()

  // Conversation Prolonger State
  private val _prolongerTopic = MutableStateFlow("Artificial Intelligence & Future of Idanre")
  val prolongerTopic: StateFlow<String> = _prolongerTopic.asStateFlow()

  private val _prolongerOutput = MutableStateFlow("AI is transforming education globally. How do you think AI tools can help students in Idanre learn local agriculture and coding faster? What's the most surprising AI invention you've heard of recently?")
  val prolongerOutput: StateFlow<String> = _prolongerOutput.asStateFlow()

  private val _isProlonging = MutableStateFlow(false)
  val isProlonging: StateFlow<Boolean> = _isProlonging.asStateFlow()

  // Code Snippet of the Day State
  private val sampleSnippets = listOf(
    CodeSnippet(
      title = "Kotlin Scope Functions: apply & let",
      category = "Kotlin",
      code = "val user = User().apply {\n    name = \"Idanre Student\"\n    age = 17\n}.let {\n    it.name.uppercase()\n}",
      explanation = "'apply' configures an object and returns the object itself, while 'let' executes a block on the object and returns the result of the block."
    ),
    CodeSnippet(
      title = "Git Stash & Pop Trick",
      category = "Terminal / Git",
      code = "git stash\ngit pull origin main\ngit stash pop",
      explanation = "Saves your current uncommitted changes temporarily, pulls the latest updates cleanly, and restores your work safely."
    ),
    CodeSnippet(
      title = "Python List Comprehension",
      category = "Python / AI",
      code = "squares = [x * x for x in range(10) if x % 2 == 0]",
      explanation = "Creates a new filtered list in a single readable line of Python code, widely used in data science and AI preprocessing."
    ),
    CodeSnippet(
      title = "Clean Architecture Separation",
      category = "Architecture",
      code = "UI -> ViewModel -> Repository -> Data Source",
      explanation = "Separates business logic from UI rendering, making apps easy to test, maintain, and scale for millions of users."
    )
  )

  private val _currentSnippetIndex = MutableStateFlow(0)
  val currentSnippet: StateFlow<CodeSnippet> = MutableStateFlow(sampleSnippets[0])

  // Tech AI Tutor Chat State
  private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
    listOf(
      ChatMessage(
        text = "Hello! I am Tech AI, your friendly computer science and coding tutor here in Idanre. Ask me anything about coding, AI, or computers!",
        isUser = false
      )
    )
  )
  val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()
  private val _isAiThinking = MutableStateFlow(false)
  val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

  // Student Profile & Gamification
  private val _studentPoints = MutableStateFlow(150)
  val studentPoints: StateFlow<Int> = _studentPoints.asStateFlow()

  private val _studyStreak = MutableStateFlow(5)
  val studyStreak: StateFlow<Int> = _studyStreak.asStateFlow()

  fun updateSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun selectTab(tab: SearchTab) {
    _selectedTab.value = tab
    if (_searchQuery.value.isNotBlank()) {
      performSearch(_searchQuery.value)
    }
  }

  fun performSearch(word: String) {
    if (word.isBlank()) return
    _searchQuery.value = word
    _isLoading.value = true
    _errorMessage.value = null

    viewModelScope.launch {
      try {
        when (_selectedTab.value) {
          SearchTab.RHYMES -> {
            val res = DatamuseClient.api.getRhymes(word.trim())
            _searchResults.value = res
          }
          SearchTab.NEAR_RHYMES -> {
            val res = DatamuseClient.api.getNearRhymes(word.trim())
            _searchResults.value = res
          }
          SearchTab.SYNONYMS -> {
            val res = DatamuseClient.api.getSynonyms(word.trim())
            _searchResults.value = res
          }
          SearchTab.ANTONYMS -> {
            val res = DatamuseClient.api.getAntonyms(word.trim())
            _searchResults.value = res
          }
          SearchTab.AI_SONGWRITER -> {
            _isLoading.value = true
            val aiText = geminiService.generateSongwritingIdeas(word.trim(), "Afrobeats / Highlife")
            _aiSongwritingResult.value = aiText
            _searchResults.value = emptyList()
          }
        }
        _studentPoints.update { it + 5 }
      } catch (e: Exception) {
        _errorMessage.value = "Search failed: ${e.localizedMessage}. Check internet."
      } finally {
        _isLoading.value = false
      }
    }
  }

  fun updateArticleInput(text: String) {
    _articleInput.value = text
  }

  fun summarizeArticle(text: String) {
    if (text.isBlank()) return
    _articleInput.value = text
    _isSummarizing.value = true
    viewModelScope.launch {
      val prompt = "Distill the following article text into exactly three powerful bullet-point key takeaways in simple, clear English:\n\n$text"
      val summary = geminiService.askTechAi(prompt)
      _summaryResult.value = summary
      _isSummarizing.value = false
      _studentPoints.update { it + 15 }
    }
  }

  fun generateGratitudeNote(recipient: String, occasion: String) {
    _isGeneratingGratitude.value = true
    viewModelScope.launch {
      val prompt = "Write a warm, sincere, and brief appreciation message for $recipient regarding $occasion. Keep it uplifting and professional yet personal."
      val note = geminiService.askTechAi(prompt)
      _gratitudeMessage.value = note
      _isGeneratingGratitude.value = false
      _studentPoints.update { it + 10 }
    }
  }

  fun generateIcebreaker(category: String) {
    _isGeneratingIcebreaker.value = true
    viewModelScope.launch {
      val prompt = "Generate one thoughtful, engaging, and fun conversation starter question suitable for $category. Keep it concise."
      val q = geminiService.askTechAi(prompt)
      _currentIcebreaker.value = q
      _isGeneratingIcebreaker.value = false
      _studentPoints.update { it + 10 }
    }
  }

  fun swirlConversation(topic: String) {
    _prolongerTopic.value = topic
    _isProlonging.value = true
    viewModelScope.launch {
      val prompt = "The current conversation topic is '$topic'. Never let the conversation get boring! Give a fascinating 2-sentence concept leap or surprising trivia about this topic, followed by an intriguing, thought-provoking question to keep the conversation rolling endlessly."
      val swirl = geminiService.askTechAi(prompt)
      _prolongerOutput.value = swirl
      _isProlonging.value = false
      _studentPoints.update { it + 10 }
    }
  }

  fun nextSnippet() {
    _currentSnippetIndex.update { (it + 1) % sampleSnippets.size }
    (currentSnippet as MutableStateFlow<CodeSnippet>).value = sampleSnippets[_currentSnippetIndex.value]
  }

  fun sendTutorMessage(prompt: String) {
    if (prompt.isBlank()) return
    val userMsg = ChatMessage(text = prompt.trim(), isUser = true)
    _chatMessages.update { it + userMsg }
    _isAiThinking.value = true

    viewModelScope.launch {
      val replyText = geminiService.askTechAi(prompt.trim())
      val aiMsg = ChatMessage(text = replyText, isUser = false)
      _chatMessages.update { it + aiMsg }
      _isAiThinking.value = false
      _studentPoints.update { it + 10 }
    }
  }
}
