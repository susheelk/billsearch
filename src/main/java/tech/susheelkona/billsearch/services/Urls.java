package tech.susheelkona.billsearch.services;

public interface Urls {
    String LEGISINFO_LATEST_ORDER = "https://www.parl.ca/LegisInfo/Home.aspx?ParliamentSession=43-1&SortBy=BillLatestEventStartTime&SortDir=DESC&Language=E&download=xml";
    String LEGISINFO_VOTES = "https://www.ourcommons.ca/Parliamentarians/en/HouseVotes/ExportVotes?output=XML";
    String LEGISINFO_BALLOT = "https://www.ourcommons.ca/Parliamentarians/en/HouseVotes/ExportDetailsVotes?output=XML&parliament=43&session=1&vote=";
    String LEGISINFO_VOTE_HTML = "https://www.ourcommons.ca/Parliamentarians/en/votes/43/1/";
    String LEGISINFO_MINISTERS = "https://www.ourcommons.ca/Parliamentarians/en/ministries/Export?output=XML";
    String LEGISINFO_UPDATES = "https://www.parl.ca/LegisInfo/RSSFeed.aspx?download=rss&Language=E&source=LegislationEvents";
}
