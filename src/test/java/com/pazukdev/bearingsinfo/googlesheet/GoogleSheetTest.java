package com.pazukdev.bearingsinfo.googlesheet;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
public class GoogleSheetTest {

    private static final String SPREADSHEET_URL = "https://docs.google.com/spreadsheets/d/1aaXH_wLpRSYWSTcvsvy6XEKrdMQaB2z6C7Pkc1PEljc/edit?usp=sharing";

    @Ignore
    @Test
    public void getSheetsTest() throws IOException, ServiceException {
        SpreadsheetService service = new SpreadsheetService("MySpreadsheetIntegration-v1");

        // TODO: Authorize the service object for a specific user (see other sections)

        SpreadsheetFeed feed = service.getFeed(new URL(SPREADSHEET_URL), SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

        Assert.assertEquals(1, spreadsheets.size());
        Assert.assertEquals("seal", spreadsheets.get(0).getTitle().getPlainText());
    }

}
