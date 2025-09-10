package com.example.data

import com.example.data.model.HeadlineResponse
import com.example.data.model.Source
import com.example.domain.ArticleDto
import com.example.domain.MainRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DataUnitTest {

    private lateinit var remoteDataSourceImpl: MockRemoteDataSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var mockRepository: MockRepository

    @Before
    fun setup() {
        remoteDataSourceImpl = MockRemoteDataSource()
        localDataSource = LocalDataSource()
        mockRepository = MockRepository(localDataSource, remoteDataSourceImpl)
    }

    @Test
    fun `아티클_리스트_컴바인_테스트`() = runBlocking {
        //Give
        val source1 = Source(id ="associated-press", name = "Associated Press")
        val source2 = Source(id ="cbs-news", name = "CBS News")
        val source3 = Source(id = null, name = "Anduril.com")

        localDataSource.addFavorite(source = source1)
        localDataSource.addFavorite(source = source2)
        localDataSource.addFavorite(source = source3)

        val result1 = mockRepository.getTopHeadlines("us", "business")
        val count1 = result1.first().count { it.bookmarked == true }
        assertEquals(3, count1)


        //Give
        localDataSource.removeFavorite(source = source3)
        localDataSource.removeFavorite(source = source1)

        val result2 = mockRepository.getTopHeadlines("us", "business")
        val count2 = result2.first().count { it.bookmarked == true }
        assertEquals(1, count2)

        localDataSource.removeFavorite(source = source2)
        val result3 = mockRepository.getTopHeadlines("us", "business")
        val count3 = result3.first().count { it.bookmarked == true }
        assertEquals(0, count3)
    }
}

class MockRepository(private val localDataSource: LocalDataSource, private val remoteDataSourceImpl: MockRemoteDataSource) : MainRepository {

    override suspend fun getTopHeadlines(
        country: String,
        category: String
    ): Flow<List<ArticleDto>> {

        return withContext(Dispatchers.IO) {
            val local = localDataSource.getFavoriteList()
            val remote = remoteDataSourceImpl.getHeadLineArticles(country,category)

            combine(local, remote) { localList, remoteList ->
                val result = remoteList.articles.map { article ->
                    val articleDto = article.toArticleDto()
                    val isBookmarked = localList.any { favoriteSource ->
                        favoriteSource.name == article.source.name &&
                                favoriteSource.id == article.source.id
                    }
                    articleDto.copy(bookmarked = isBookmarked)
                }
                result
            }
        }
    }
}

class MockRemoteDataSource : RemoteDataSourceImpl() {

    override fun getHeadLineArticles(
        country: String,
        category: String
    ): Flow<HeadlineResponse> = flow {
        val jsonString = """{"status":"ok","totalResults":49,"articles":[{"source":{"id":"the-washington-post","name":"The Washington Post"},"author":"Jeremy Barr, Sarah Ellison","title":"Murdoch family resolves dispute over ownership in multibillion-dollar deal - The Washington Post","description":"Rupert Murdoch and his son Lachlan reached a ${'$'}3.3 billion settlement with Murdoch’s three older children, giving Lachlan control of the media empire after his father’s death.","url":"https://www.washingtonpost.com/business/2025/09/08/rupert-murdoch-lachlan-family-settlement/","urlToImage":"https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/M2EBPZQLDJE3LNRYFSBP2RGWLQ.jpg&w=1440","publishedAt":"2025-09-09T01:46:49Z","content":"The future ownership of the Murdoch media empire has finally been resolved.\r\nRupert Murdoch and his son Lachlan have reached an agreement with the patriarchs other children that will resolve thebitte… [+95 chars]"},{"source":{"id":"associated-press","name":"Associated Press"},"author":"Michael Liedtke, Matt O'Brien","title":"Judge skewers ${'$'}1.5B Anthropic settlement with authors in pirated books case over AI training - AP News","description":"A federal judge on Monday skewered a ${'$'}1.5 billion settlement between artificial intelligence company Anthropic and authors who allege nearly half million books had been illegally pirated to train chatbots, raising the specter that the case could still end up …","url":"https://apnews.com/article/anthropic-authors-book-settlement-ai-copyright-claude-b282fe615338bf1f98ad97cb82e978a1","urlToImage":"https://dims.apnews.com/dims4/default/d6fe99b/2147483647/strip/true/crop/5760x3240+0+21/resize/1440x810!/quality/90/?url=https%3A%2F%2Fassets.apnews.com%2Fc9%2F05%2F2d0e3b4fba0945fbf279c6fa1a27%2F275efa35532f4733ad0b98acac8d2fce","publishedAt":"2025-09-09T00:28:00Z","content":"SAN FRANCISCO (AP) A federal judge on Monday skewered a ${'$'}1.5 billion settlement between artificial intelligence company Anthropic and authors who allege nearly half a million books had been illegally… [+4473 chars]"},{"source":{"id":"bloomberg","name":"Bloomberg"},"author":"Crystal Tse, Dinesh Nair, Thomas Biesheuvel, Thomas Seal","title":"Anglo American Near Deal to Acquire Teck Resources - Bloomberg.com","description":"Anglo American Plc is nearing a deal to acquire Canada’s Teck Resources Ltd. in what could be the biggest mining deal in more than a decade — and just over a year since Anglo was subject to a takeover bid itself.","url":"https://www.bloomberg.com/news/articles/2025-09-08/anglo-american-is-said-to-near-deal-to-acquire-teck-resources","urlToImage":"https://assets.bwbx.io/images/users/iqjWHBFdfxIU/iVru6gMHTBbA/v0/1200x800.jpg","publishedAt":"2025-09-08T23:50:31Z","content":"Anglo American Plc is nearing a deal to acquire Canadas Teck Resources Ltd. in what could be the biggest mining deal in more than a decade and just over a year since Anglo was subject to a takeover b… [+341 chars]"},{"source":{"id":null,"name":"BBC News"},"author":null,"title":"Ferrari chair John Elkann to do community service over tax case - BBC","description":"John Elkann and two of his siblings will pay €183m to settle an Italian inheritance tax dispute.","url":"https://www.bbc.com/news/articles/c8d7q99yd06o","urlToImage":"https://ichef.bbci.co.uk/news/1024/branded_news/c82b/live/5200f700-8cf0-11f0-afcc-0100a3263099.jpg","publishedAt":"2025-09-08T23:36:20Z","content":"Rachel ClunBusiness reporter, BBC News\r\nThe chair of Ferrari and Stellantis has agreed to do one year of community service and jointly pay millions of euros to settle a dispute over inheritance tax i… [+2669 chars]"},{"source":{"id":null,"name":"CNBC"},"author":"Sarah Min","title":"Stock futures are little changed after Nasdaq hits new record: Live updates - CNBC","description":"Wall Street is coming off a winning session, thanks to an advance in key chip stocks.","url":"https://www.cnbc.com/2025/09/08/stock-market-today-live-updates.html","urlToImage":"https://image.cnbcfm.com/api/v1/image/108190796-1756217321266-gettyimages-2232189512-mms27807_k9vjva0q.jpeg?v=1756217934&w=1920&h=1080","publishedAt":"2025-09-08T23:29:00Z","content":"U.S. stock futures were little changed on Monday night after the Nasdaq Composite hit a new record.\r\nDow Jones Industrial Average futures rose by 57 points, or 0.13%. S&amp;P 500 futures and Nasdaq 1… [+1850 chars]"},{"source":{"id":null,"name":"Forbes"},"author":"Phoebe Liu","title":"Sam Altman Is Selling His Most Valuable Known Property - Forbes","description":"The listing comes as OpenAI gears up for an astronomical employee share sale that could make many in OpenAI’s ranks rich enough to afford the mansion.","url":"https://www.forbes.com/sites/phoebeliu/2025/09/08/sam-altman-is-selling-off-his-hawaii-mega-estate/","urlToImage":"https://imageio.forbes.com/specials-images/imageserve/68bf58cc7a17cf5eed533310/0x0.jpg?format=jpg&height=900&width=1600&fit=bounds","publishedAt":"2025-09-08T22:38:53Z","content":"Billionaire OpenAI CEO Sam Altman at the Sun Valley lodge for the Allen &amp; Company Sun Valley Conference in July.\r\nGetty Images\r\nOpenAI cofounder and CEO Sam Altman is selling his Hawaii house for… [+2488 chars]"},{"source":{"id":"fortune","name":"Fortune"},"author":"Jessica Mathews","title":"DoorDash CEO Tony Xu says path to autonomous deliveries filled with 'lots of pain and suffering' but company is nearing first inning of commercial progress - Fortune","description":"DoorDash has been working on autonomy and robotics technology since about 2017. It's currently doing drone deliveries in Australia and securing permits for the U.S.","url":"https://fortune.com/2025/09/08/doordash-ceo-tony-xu-interview-brainstorm-tech-autonomous-drone-deliveries/","urlToImage":"https://fortune.com/img-assets/wp-content/uploads/2025/09/54774887323_1afcb7083e_o-e1757369467910.jpg?resize=1200,600","publishedAt":"2025-09-08T22:21:00Z","content":"Tony Xu, cofounder and CEO of food and grocery giant DoorDash, doesnt sugar coat the companys efforts, and challenges, developing autonomous delivery technologies.Candidly, its mostly been filled wit… [+2500 chars]"},{"source":{"id":null,"name":"KKTV 11 News"},"author":"Aspen Andrews","title":"${'$'}1 million Powerball ticket purchased in Colorado Springs - KKTV","description":"While no one in Colorado won this weekend’s jackpot, one ticket purchased in Colorado Springs won a player ${'$'}1 million in Saturday’s Powerball drawing!","url":"https://www.kktv.com/2025/09/08/colorado-springs-resident-wins-more-than-1-million-powerball-drawing/","urlToImage":"https://gray-kktv-prod.gtv-cdn.com/resizer/v2/QZCITLJX7ZBJDK76OFP6QUXEHQ.jpg?auth=060485d3b60f408e1095a8195e070ab9259764feec5ca61a8bd194ffe0336272&width=1200&height=600&smart=true","publishedAt":"2025-09-08T21:14:00Z","content":"COLORADO SPRINGS, Colo. (KKTV) - While no one in Colorado won this weekends jackpot, one ticket purchased in Colorado Springs won a player ${'$'}1 million in Saturdays Powerball drawing!\r\nA spokesperson w… [+775 chars]"},{"source":{"id":"the-wall-street-journal","name":"The Wall Street Journal"},"author":"The Wall Street Journal","title":"Lumber Prices Are Flashing a Warning Sign for the U.S. Economy - The Wall Street Journal","description":null,"url":"https://www.wsj.com/finance/commodities-futures/lumber-prices-are-flashing-a-warning-sign-for-the-u-s-economy-2119c5dc","urlToImage":null,"publishedAt":"2025-09-08T20:35:00Z","content":null},{"source":{"id":"cbs-news","name":"CBS News"},"author":"Aimee  Picchi","title":"Major jobs revision Tuesday could show the labor market is weaker than previously thought - CBS News","description":"Economists expect the Bureau of Labor Statistics to revise its jobs data downward for the year ended in March 2025. Here's why.","url":"https://www.cbsnews.com/news/jobs-report-revision-september-9-bls-economy-trump/","urlToImage":"https://assets3.cbsnewsstatic.com/hub/i/r/2025/08/01/ed9090eb-af3c-4418-b908-14da1aba5f6a/thumbnail/1200x630g6/7aa8a2df415021486d7a9376b9981c28/gettyimages-1395278718.jpg","publishedAt":"2025-09-08T20:32:00Z","content":"The latest jobs report points to a labor market that's faltering, but the slowdown may have begun much earlier. Economists expect the Bureau of Labor Statistics tomorrow to issue a major downward rev… [+6320 chars]"},{"source":{"id":null,"name":"Barron's"},"author":"Barron's","title":"AppLovin, Robinhood, Broadcom, Tesla, EchoStar, Planet Labs, QuantumScape, and More Movers - Barron's","description":null,"url":"https://www.barrons.com/articles/stock-movers-5a703061","urlToImage":null,"publishedAt":"2025-09-08T20:16:00Z","content":null},{"source":{"id":null,"name":"Intc.com"},"author":null,"title":"Intel Announces Key Leadership Appointments to Accelerate Innovation and Strengthen Execution - intc.com","description":null,"url":"https://www.intc.com/news-events/press-releases/detail/1749/intel-announces-key-leadership-appointments-to-accelerate","urlToImage":"https://d1io3yog0oux5.cloudfront.net/_d695306582e6f1104214b69298ca5ad9/intel/db/878/6995/social_image_resized.jpg","publishedAt":"2025-09-08T20:14:57Z","content":"SANTA CLARA, Calif.--(BUSINESS WIRE)--\r\nIntel Corporation today announced a series of senior leadership appointments that support the companys strategy to strengthen its core product business, build … [+5703 chars]"},{"source":{"id":"fortune","name":"Fortune"},"author":"Ashley Lutz","title":"RFK Jr.'s planned report linking Tylenol to autism crashes shares of parent company Kenvue - Fortune","description":"RFK Jr. is preparing a report that will allegedly claim a link between prenatal Tylenol (acetaminophen) use and autism.","url":"https://fortune.com/2025/09/08/rfk-jr-planned-report-tylenol-autism-kenvue/","urlToImage":"https://fortune.com/img-assets/wp-content/uploads/2025/09/GettyImages-2233689729.jpg?resize=1200,600","publishedAt":"2025-09-08T20:11:00Z","content":"Ashley Lutz is an executive editor at Fortune, overseeing the Success, Well, syndication, and social teams. She was previously an editorial leader at Bankrate, The Points Guy, and BusinessInsider, an… [+102 chars]"},{"source":{"id":null,"name":"Investor's Business Daily"},"author":null,"title":"Tesla EV Sales Are Struggling But Elon Musk Looks To The Future For Value - Investor's Business Daily","description":"Tesla market share in the U.S. has fallen to its lowest level in nearly eight years.","url":"https://www.investors.com/news/tesla-ev-sales-struggling-elon-musk-looking-to-future/","urlToImage":"https://www.investors.com/wp-content/uploads/2025/01/Stock-Tesla-NewModelY-02-company.jpg","publishedAt":"2025-09-08T20:11:00Z","content":"Information in Investors Business Daily is for informational and educational purposes only and should not be construed as an offer, recommendation, solicitation, or rating to buy or sell securities. … [+1064 chars]"},{"source":{"id":null,"name":"Scrippsnews.com"},"author":"AP via Scripps News Group","title":"Salmonella outbreak linked to home delivery meals sickens over a dozen people - Scripps News","description":"A Salmonella outbreak has been linked to home delivery meals made by Metabolic Meals, prompting health officials to warn customers to check their refrigerators and freezers.","url":"https://www.scrippsnews.com/life/recalls/salmonella-outbreak-linked-to-home-delivery-meals-sickens-over-a-dozen-people","urlToImage":"https://ewscripps.brightspotcdn.com/dims4/default/2b85c2e/2147483647/strip/true/crop/2652x1392+0+23/resize/1200x630!/quality/90/?url=http%3A%2F%2Fewscripps-brightspot.s3.amazonaws.com%2Fe9%2F1f%2F2d1e87d448f4a86874410f36e2f0%2Fscreenshot-2025-09-08-at-12-26-38-pm.png","publishedAt":"2025-09-08T19:28:00Z","content":"A Salmonella outbreak has been linked to home delivery meals made by Metabolic Meals, prompting health officials to warn customers to check their refrigerators and freezers.\r\nAccording to the Centers… [+1409 chars]"},{"source":{"id":null,"name":"Anduril.com"},"author":null,"title":"Anduril Awarded Contract to Redefine the Future of Mixed Reality - Anduril","description":"Anduril Industries announced today that it has been awarded a ${'$'}159 million contract by the U.S. Army for an initial prototyping period to develop a night vision and mixed reality system as part of the Soldier Borne Mission Command (formerly IVAS Next) program.","url":"https://www.anduril.com/article/anduril-awarded-contract-to-redefine-the-future-of-mixed-reality/","urlToImage":"https://cdn.sanity.io/images/z5s3oquj/production/7bed263ed40c7bd0f9ecd1ddd62983f59a6222fc-8000x4500.png?rect=0,250,8000,4000&w=1200&h=600&q=80","publishedAt":"2025-09-08T18:59:57Z","content":null}]}"""
        val headlineResponse = parseHeadlineResponse(jsonString)
        emit(headlineResponse)
    }
}