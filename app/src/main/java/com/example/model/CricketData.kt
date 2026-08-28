package com.example.model

data class TeamScore(
  val name: String,
  val score: String,
  val overs: String,
  val rr: String = "",
  val reqRr: String? = null
)

data class BatsmanLive(
  val name: String,
  val runs: Int,
  val balls: Int
)

data class BowlerLive(
  val name: String,
  val overs: String,
  val runs: Int,
  val wkts: Int
)

data class LiveMatch(
  val id: Int,
  val format: String,
  val series: String,
  val venue: String,
  val teamA: TeamScore,
  val teamB: TeamScore,
  val summary: String,
  val thisOver: List<String>,
  val batsmen: List<BatsmanLive>,
  val bowler: BowlerLive
)

data class UpcomingMatch(
  val id: Int,
  val format: String,
  val series: String,
  val teamA: String,
  val teamB: String,
  val time: String
)

data class BattingCardEntry(
  val name: String,
  val info: String,
  val runs: Int,
  val balls: Int,
  val fours: Int,
  val sixes: Int,
  val sr: String
)

data class BowlingCardEntry(
  val name: String,
  val overs: Any,
  val maidens: Int,
  val runs: Int,
  val wickets: Int,
  val econ: String
)

data class InningsCard(
  val team: String,
  val total: String,
  val batting: List<BattingCardEntry>,
  val bowling: List<BowlingCardEntry>
)

data class MatchScorecard(
  val matchId: Int,
  val result: String,
  val innings: List<InningsCard>
)

data class PlayerStats(
  val runs: Int? = null,
  val wkts: Int? = null,
  val avg: String,
  val sr: String? = null,
  val econ: String? = null
)

data class Player(
  val id: Int,
  val name: String,
  val country: String,
  val role: String,
  val odi: PlayerStats,
  val t20: PlayerStats,
  val test: PlayerStats
)

data class RankItem(
  val rank: Int,
  val name: String,
  val country: String,
  val rating: Int
)

data class SquadPlayer(
  val name: String,
  val role: String,
  val cap: String? = null
)

object CricketRepository {

  val LIVE_MATCHES = listOf(
    LiveMatch(
      id = 1,
      format = "T20",
      series = "AHX T20 Cup, 2026",
      venue = "National Stadium, Karachi",
      teamA = TeamScore(name = "Pakistan", score = "186/4", overs = "18.2", rr = "10.14"),
      teamB = TeamScore(name = "New Zealand", score = "142/7", overs = "16.0", rr = "8.87", reqRr = "12.00"),
      summary = "PAK need 8 runs off 10 balls",
      thisOver = listOf("1", "4", "W", "6", "0", "1"),
      batsmen = listOf(
        BatsmanLive("Babar Azam", 78, 44),
        BatsmanLive("Mohammad Rizwan", 34, 18)
      ),
      bowler = BowlerLive("Trent Boult", "3.2", 38, 1)
    ),
    LiveMatch(
      id = 2,
      format = "ODI",
      series = "Tri-Series, 2026",
      venue = "MCG, Melbourne",
      teamA = TeamScore(name = "Australia", score = "298/6", overs = "50.0", rr = "5.96"),
      teamB = TeamScore(name = "India", score = "134/2", overs = "22.4", rr = "5.91", reqRr = "6.02"),
      summary = "IND require 165 runs off 165 balls",
      thisOver = listOf("0", "1", "1", "4", "0", "2"),
      batsmen = listOf(
        BatsmanLive("Shubman Gill", 67, 71),
        BatsmanLive("Virat Kohli", 41, 55)
      ),
      bowler = BowlerLive("Pat Cummins", "8.4", 42, 1)
    ),
    LiveMatch(
      id = 3,
      format = "TEST",
      series = "World Test Championship",
      venue = "Lord's, London",
      teamA = TeamScore(name = "England", score = "412 & 88/2", overs = "Day 4", rr = ""),
      teamB = TeamScore(name = "South Africa", score = "356", overs = "", rr = ""),
      summary = "England lead by 144 runs",
      thisOver = listOf("0", "0", "1", "0", "4", "0"),
      batsmen = listOf(
        BatsmanLive("Joe Root", 51, 98),
        BatsmanLive("Harry Brook", 22, 40)
      ),
      bowler = BowlerLive("Kagiso Rabada", "14.0", 39, 1)
    )
  )

  val UPCOMING_MATCHES = listOf(
    UpcomingMatch(id = 4, format = "ODI", series = "Asia Cup Qualifiers", teamA = "Bangladesh", teamB = "Sri Lanka", time = "Tomorrow, 2:30 PM"),
    UpcomingMatch(id = 5, format = "T20", series = "AHX T20 Cup, 2026", teamA = "West Indies", teamB = "Afghanistan", time = "29 Aug, 7:00 PM"),
    UpcomingMatch(id = 6, format = "TEST", series = "Border-Gavaskar Trophy", teamA = "India", teamB = "Australia", time = "02 Sep, 10:00 AM")
  )

  val SCORECARDS: Map<Int, MatchScorecard> = mapOf(
    1 to MatchScorecard(
      matchId = 1,
      result = "PAK won by 3 wickets",
      innings = listOf(
        InningsCard(
          team = "Pakistan",
          total = "186/4 (18.2 overs)",
          batting = listOf(
            BattingCardEntry("Babar Azam", "not out", 78, 44, 6, 3, "177.3"),
            BattingCardEntry("Fakhar Zaman", "c Conway b Santner", 52, 31, 5, 2, "167.7"),
            BattingCardEntry("Mohammad Rizwan", "not out", 34, 18, 3, 1, "188.9")
          ),
          bowling = listOf(
            BowlingCardEntry("Trent Boult", "4.0", 0, 38, 1, "9.5"),
            BowlingCardEntry("Mitchell Santner", "4.0", 0, 29, 1, "7.3")
          )
        ),
        InningsCard(
          team = "New Zealand",
          total = "142/7 (16.0 overs)",
          batting = listOf(
            BattingCardEntry("Kane Williamson", "c Rizwan b Afridi", 45, 30, 4, 1, "150.0"),
            BattingCardEntry("Devon Conway", "b Naseem", 38, 26, 3, 2, "146.1")
          ),
          bowling = listOf(
            BowlingCardEntry("Shaheen Afridi", "4.0", 0, 31, 3, "7.8"),
            BowlingCardEntry("Naseem Shah", "3.0", 0, 27, 2, "9.0")
          )
        )
      )
    ),
    2 to MatchScorecard(
      matchId = 2,
      result = "IND need 165 runs to win",
      innings = listOf(
        InningsCard(
          team = "Australia",
          total = "298/6 (50.0 overs)",
          batting = listOf(
            BattingCardEntry("Steve Smith", "c Rahul b Bumrah", 84, 92, 7, 1, "91.3"),
            BattingCardEntry("Marnus Labuschagne", "b Jadeja", 62, 74, 5, 0, "83.7"),
            BattingCardEntry("Alex Carey", "not out", 48, 35, 4, 2, "137.1")
          ),
          bowling = listOf(
            BowlingCardEntry("Jasprit Bumrah", "10.0", 1, 48, 3, "4.8"),
            BowlingCardEntry("Ravindra Jadeja", "10.0", 0, 52, 2, "5.2")
          )
        ),
        InningsCard(
          team = "India",
          total = "134/2 (22.4 overs)",
          batting = listOf(
            BattingCardEntry("Shubman Gill", "batting", 67, 71, 7, 2, "94.3"),
            BattingCardEntry("Virat Kohli", "batting", 41, 55, 3, 0, "74.5")
          ),
          bowling = listOf(
            BowlingCardEntry("Pat Cummins", "8.4", 0, 42, 1, "4.8"),
            BowlingCardEntry("Mitchell Starc", "7.0", 0, 45, 1, "6.4")
          )
        )
      )
    ),
    3 to MatchScorecard(
      matchId = 3,
      result = "England lead by 144 runs",
      innings = listOf(
        InningsCard(
          team = "England",
          total = "412 & 88/2",
          batting = listOf(
            BattingCardEntry("Joe Root", "batting", 51, 98, 6, 0, "52.0"),
            BattingCardEntry("Harry Brook", "batting", 22, 40, 3, 0, "55.0"),
            BattingCardEntry("Ben Duckett", "c Bavuma b Rabada", 42, 60, 5, 0, "70.0")
          ),
          bowling = listOf(
            BowlingCardEntry("Kagiso Rabada", "14.0", 3, 39, 1, "2.7"),
            BowlingCardEntry("Marco Jansen", "12.0", 2, 34, 1, "2.8")
          )
        ),
        InningsCard(
          team = "South Africa",
          total = "356 (104.2 overs)",
          batting = listOf(
            BattingCardEntry("Temba Bavuma", "c Root b Anderson", 95, 180, 11, 0, "52.7"),
            BattingCardEntry("Aiden Markram", "c Duckett b Stokes", 68, 120, 8, 1, "56.6")
          ),
          bowling = listOf(
            BowlingCardEntry("James Anderson", "24.0", 6, 68, 4, "2.8"),
            BowlingCardEntry("Mark Wood", "18.2", 2, 72, 3, "3.9")
          )
        )
      )
    )
  )

  val PLAYERS = listOf(
    Player(
      id = 1,
      name = "Babar Azam",
      country = "Pakistan",
      role = "Batsman",
      odi = PlayerStats(runs = 5729, avg = "56.2", sr = "88.9"),
      t20 = PlayerStats(runs = 3485, avg = "42.1", sr = "129.5"),
      test = PlayerStats(runs = 3839, avg = "45.7", sr = "54.2")
    ),
    Player(
      id = 2,
      name = "Virat Kohli",
      country = "India",
      role = "Batsman",
      odi = PlayerStats(runs = 13906, avg = "58.0", sr = "93.6"),
      t20 = PlayerStats(runs = 4188, avg = "48.7", sr = "137.0"),
      test = PlayerStats(runs = 8848, avg = "46.9", sr = "56.0")
    ),
    Player(
      id = 3,
      name = "Shaheen Afridi",
      country = "Pakistan",
      role = "Bowler",
      odi = PlayerStats(wkts = 174, avg = "24.3", econ = "5.1"),
      t20 = PlayerStats(wkts = 98, avg = "20.8", econ = "7.4"),
      test = PlayerStats(wkts = 191, avg = "23.1", econ = "3.2")
    ),
    Player(
      id = 4,
      name = "Pat Cummins",
      country = "Australia",
      role = "Bowler",
      odi = PlayerStats(wkts = 165, avg = "25.6", econ = "5.4"),
      t20 = PlayerStats(wkts = 70, avg = "22.9", econ = "8.1"),
      test = PlayerStats(wkts = 294, avg = "22.4", econ = "2.9")
    ),
    Player(
      id = 5,
      name = "Joe Root",
      country = "England",
      role = "Batsman",
      odi = PlayerStats(runs = 6474, avg = "47.6", sr = "87.5"),
      t20 = PlayerStats(runs = 972, avg = "28.6", sr = "126.4"),
      test = PlayerStats(runs = 13378, avg = "50.9", sr = "56.4")
    ),
    Player(
      id = 6,
      name = "Kane Williamson",
      country = "New Zealand",
      role = "Batsman",
      odi = PlayerStats(runs = 6844, avg = "47.5", sr = "81.2"),
      t20 = PlayerStats(runs = 2464, avg = "32.9", sr = "123.6"),
      test = PlayerStats(runs = 9276, avg = "54.9", sr = "51.1")
    ),
    Player(
      id = 7,
      name = "Trent Boult",
      country = "New Zealand",
      role = "Bowler",
      odi = PlayerStats(wkts = 211, avg = "24.9", econ = "5.3"),
      t20 = PlayerStats(wkts = 113, avg = "21.4", econ = "7.6"),
      test = PlayerStats(wkts = 317, avg = "27.5", econ = "3.0")
    ),
    Player(
      id = 8,
      name = "Mohammad Rizwan",
      country = "Pakistan",
      role = "Wicketkeeper",
      odi = PlayerStats(runs = 3184, avg = "45.5", sr = "88.7"),
      t20 = PlayerStats(runs = 4237, avg = "45.1", sr = "133.3"),
      test = PlayerStats(runs = 1958, avg = "36.9", sr = "58.1")
    ),
    Player(
      id = 9,
      name = "Jasprit Bumrah",
      country = "India",
      role = "Bowler",
      odi = PlayerStats(wkts = 190, avg = "23.9", econ = "4.6"),
      t20 = PlayerStats(wkts = 92, avg = "19.6", econ = "6.6"),
      test = PlayerStats(wkts = 216, avg = "20.1", econ = "2.6")
    ),
    Player(
      id = 10,
      name = "Marnus Labuschagne",
      country = "Australia",
      role = "Batsman",
      odi = PlayerStats(runs = 1512, avg = "43.2", sr = "83.6"),
      t20 = PlayerStats(runs = 486, avg = "31.4", sr = "119.8"),
      test = PlayerStats(runs = 5928, avg = "51.7", sr = "58.0")
    ),
    Player(
      id = 11,
      name = "Kagiso Rabada",
      country = "South Africa",
      role = "Bowler",
      odi = PlayerStats(wkts = 173, avg = "26.8", econ = "5.5"),
      t20 = PlayerStats(wkts = 100, avg = "23.1", econ = "8.0"),
      test = PlayerStats(wkts = 320, avg = "22.6", econ = "3.4")
    ),
    Player(
      id = 12,
      name = "Rashid Khan",
      country = "Afghanistan",
      role = "Bowler",
      odi = PlayerStats(wkts = 178, avg = "18.9", econ = "4.1"),
      t20 = PlayerStats(wkts = 187, avg = "16.2", econ = "6.2"),
      test = PlayerStats(wkts = 34, avg = "26.4", econ = "3.0")
    ),
    Player(
      id = 13,
      name = "Fakhar Zaman",
      country = "Pakistan",
      role = "Batsman",
      odi = PlayerStats(runs = 4318, avg = "45.9", sr = "94.5"),
      t20 = PlayerStats(runs = 2308, avg = "31.2", sr = "128.9"),
      test = PlayerStats(runs = 552, avg = "27.6", sr = "63.1")
    ),
    Player(
      id = 14,
      name = "Mitchell Santner",
      country = "New Zealand",
      role = "All-rounder",
      odi = PlayerStats(wkts = 118, avg = "34.1", econ = "5.0"),
      t20 = PlayerStats(wkts = 105, avg = "22.6", econ = "7.0"),
      test = PlayerStats(wkts = 152, avg = "33.8", econ = "2.9")
    ),
    Player(
      id = 15,
      name = "Suryakumar Yadav",
      country = "India",
      role = "Batsman",
      odi = PlayerStats(runs = 1231, avg = "31.6", sr = "104.8"),
      t20 = PlayerStats(runs = 3387, avg = "40.3", sr = "175.7"),
      test = PlayerStats(runs = 128, avg = "25.6", sr = "62.4")
    )
  )

  val RANKINGS: Map<String, Map<String, List<RankItem>>> = mapOf(
    "TEST" to mapOf(
      "batting" to listOf(
        RankItem(1, "Harry Brook", "England", 874),
        RankItem(2, "Joe Root", "England", 850),
        RankItem(3, "Steve Smith", "Australia", 840),
        RankItem(4, "Travis Head", "Australia", 811),
        RankItem(5, "Temba Bavuma", "South Africa", 775),
        RankItem(6, "Rachin Ravindra", "New Zealand", 740),
        RankItem(7, "Rishabh Pant", "India", 711),
        RankItem(8, "Shubman Gill", "India", 711),
        RankItem(9, "Daryl Mitchell", "New Zealand", 710),
        RankItem(10, "Babar Azam", "Pakistan", 709)
      ),
      "bowling" to listOf(
        RankItem(1, "Pat Cummins", "Australia", 872),
        RankItem(2, "Shaheen Afridi", "Pakistan", 851),
        RankItem(3, "Kagiso Rabada", "South Africa", 834),
        RankItem(4, "Jasprit Bumrah", "India", 829),
        RankItem(5, "Trent Boult", "New Zealand", 805),
        RankItem(6, "Mitchell Starc", "Australia", 792),
        RankItem(7, "Nathan Lyon", "Australia", 776),
        RankItem(8, "Ravindra Jadeja", "India", 764),
        RankItem(9, "Naseem Shah", "Pakistan", 748),
        RankItem(10, "James Anderson", "England", 731)
      )
    ),
    "ODI" to mapOf(
      "batting" to listOf(
        RankItem(1, "Shubman Gill", "India", 801),
        RankItem(2, "Daryl Mitchell", "New Zealand", 794),
        RankItem(3, "Virat Kohli", "India", 767),
        RankItem(4, "Rohit Sharma", "India", 758),
        RankItem(5, "Ibrahim Zadran", "Afghanistan", 719),
        RankItem(6, "Babar Azam", "Pakistan", 689),
        RankItem(7, "Joe Root", "England", 674),
        RankItem(8, "Shai Hope", "West Indies", 673),
        RankItem(9, "Charith Asalanka", "Sri Lanka", 659),
        RankItem(10, "Harry Tector", "Ireland", 653)
      ),
      "bowling" to listOf(
        RankItem(1, "Trent Boult", "New Zealand", 858),
        RankItem(2, "Pat Cummins", "Australia", 840),
        RankItem(3, "Shaheen Afridi", "Pakistan", 822),
        RankItem(4, "Mujeeb Ur Rahman", "Afghanistan", 799),
        RankItem(5, "Kagiso Rabada", "South Africa", 781),
        RankItem(6, "Jasprit Bumrah", "India", 774),
        RankItem(7, "Mitchell Starc", "Australia", 761),
        RankItem(8, "Adam Zampa", "Australia", 748),
        RankItem(9, "Rashid Khan", "Afghanistan", 739),
        RankItem(10, "Naseem Shah", "Pakistan", 722)
      )
    ),
    "T20" to mapOf(
      "batting" to listOf(
        RankItem(1, "Ishan Kishan", "India", 910),
        RankItem(2, "Sahibzada Farhan", "Pakistan", 848),
        RankItem(3, "Abhishek Sharma", "India", 819),
        RankItem(4, "Phil Salt", "England", 799),
        RankItem(5, "Pathum Nissanka", "Sri Lanka", 751),
        RankItem(6, "Tilak Varma", "India", 750),
        RankItem(7, "Jos Buttler", "England", 748),
        RankItem(8, "Harry Brook", "England", 734),
        RankItem(9, "Mitchell Marsh", "Australia", 706),
        RankItem(10, "Dewald Brevis", "South Africa", 702)
      ),
      "bowling" to listOf(
        RankItem(1, "Shaheen Afridi", "Pakistan", 799),
        RankItem(2, "Mitchell Santner", "New Zealand", 767),
        RankItem(3, "Pat Cummins", "Australia", 742),
        RankItem(4, "Naseem Shah", "Pakistan", 730),
        RankItem(5, "Adil Rashid", "England", 715),
        RankItem(6, "Rashid Khan", "Afghanistan", 708),
        RankItem(7, "Jasprit Bumrah", "India", 697),
        RankItem(8, "Wanindu Hasaranga", "Sri Lanka", 684),
        RankItem(9, "Trent Boult", "New Zealand", 671),
        RankItem(10, "Haris Rauf", "Pakistan", 659)
      )
    )
  )

  val SQUADS: Map<String, List<SquadPlayer>> = mapOf(
    "Pakistan" to listOf(
      SquadPlayer("Babar Azam", "Batsman", "C"),
      SquadPlayer("Mohammad Rizwan", "Wicketkeeper", "VC"),
      SquadPlayer("Fakhar Zaman", "Batsman"),
      SquadPlayer("Shaheen Afridi", "Bowler"),
      SquadPlayer("Naseem Shah", "Bowler"),
      SquadPlayer("Shadab Khan", "All-rounder"),
      SquadPlayer("Haris Rauf", "Bowler"),
      SquadPlayer("Iftikhar Ahmed", "All-rounder")
    ),
    "India" to listOf(
      SquadPlayer("Virat Kohli", "Batsman"),
      SquadPlayer("Shubman Gill", "Batsman", "C"),
      SquadPlayer("Rohit Sharma", "Batsman"),
      SquadPlayer("Jasprit Bumrah", "Bowler"),
      SquadPlayer("Ravindra Jadeja", "All-rounder"),
      SquadPlayer("KL Rahul", "Wicketkeeper"),
      SquadPlayer("Hardik Pandya", "All-rounder"),
      SquadPlayer("Mohammed Siraj", "Bowler")
    ),
    "Australia" to listOf(
      SquadPlayer("Pat Cummins", "Bowler", "C"),
      SquadPlayer("Steve Smith", "Batsman"),
      SquadPlayer("Travis Head", "Batsman"),
      SquadPlayer("Mitchell Starc", "Bowler"),
      SquadPlayer("Marnus Labuschagne", "Batsman"),
      SquadPlayer("Alex Carey", "Wicketkeeper"),
      SquadPlayer("Glenn Maxwell", "All-rounder"),
      SquadPlayer("Adam Zampa", "Bowler")
    ),
    "New Zealand" to listOf(
      SquadPlayer("Kane Williamson", "Batsman", "C"),
      SquadPlayer("Devon Conway", "Batsman"),
      SquadPlayer("Trent Boult", "Bowler"),
      SquadPlayer("Mitchell Santner", "All-rounder"),
      SquadPlayer("Rachin Ravindra", "All-rounder"),
      SquadPlayer("Tom Latham", "Wicketkeeper"),
      SquadPlayer("Daryl Mitchell", "All-rounder"),
      SquadPlayer("Lockie Ferguson", "Bowler")
    ),
    "England" to listOf(
      SquadPlayer("Jos Buttler", "Wicketkeeper", "C"),
      SquadPlayer("Harry Brook", "Batsman", "VC"),
      SquadPlayer("Joe Root", "Batsman"),
      SquadPlayer("Ben Stokes", "All-rounder"),
      SquadPlayer("Jofra Archer", "Bowler"),
      SquadPlayer("Adil Rashid", "Bowler"),
      SquadPlayer("Phil Salt", "Batsman"),
      SquadPlayer("Mark Wood", "Bowler")
    ),
    "South Africa" to listOf(
      SquadPlayer("Temba Bavuma", "Batsman", "C"),
      SquadPlayer("Aiden Markram", "Batsman", "VC"),
      SquadPlayer("Kagiso Rabada", "Bowler"),
      SquadPlayer("Heinrich Klaasen", "Wicketkeeper"),
      SquadPlayer("David Miller", "Batsman"),
      SquadPlayer("Marco Jansen", "All-rounder"),
      SquadPlayer("Keshav Maharaj", "Bowler"),
      SquadPlayer("Anrich Nortje", "Bowler")
    )
  )
}
