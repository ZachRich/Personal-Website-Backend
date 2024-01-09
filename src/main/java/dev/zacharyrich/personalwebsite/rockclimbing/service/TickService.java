package dev.zacharyrich.personalwebsite.rockclimbing.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import dev.zacharyrich.personalwebsite.rockclimbing.controller.TickController;
import dev.zacharyrich.personalwebsite.rockclimbing.dao.TickDao;
import dev.zacharyrich.personalwebsite.rockclimbing.exception.TickNotFoundException;
import dev.zacharyrich.personalwebsite.rockclimbing.model.Tick;
import jakarta.transaction.Transactional;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class TickService {

    // TODO - Investigate field injection vs constructor injection
    // TODO - Make RSS_FEED_URL a configuration

    private final String RSS_FEED_URL = "https://www.mountainproject.com/rss/user-ticks/201458799";
    @Autowired
    private TickDao tickDao;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public Tick addTick(Tick tick) {
        return tickDao.save(tick);
    }

    public List<Tick> findAllTicks() {
        return tickDao.findAll();
    }

    public Tick updateTick(Tick tick) {
        return tickDao.save(tick);
    }

    public Tick findTickById(Long id) {
        return tickDao.findTickById(id)
                .orElseThrow(() ->
                        new TickNotFoundException(String.format("Tick by Id %d was not found.", id)));
    }

    public void deleteTick(Long id) {
        tickDao.deleteTickById(id);
    }

    public void fetchAndSaveRssData() {
        logger.info("Fetching and saving RSS data from Mountain Project.");

        try {
            URL rssFeedUrl = new URL(RSS_FEED_URL);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(rssFeedUrl));

            List<SyndEntry> entries = feed.getEntries();

            for (SyndEntry entry : entries) {
                String entryTitle = entry.getTitle();
                String[] parsedData = parseString(entryTitle);

                if (parsedData != null) {
                    String boulderName = parseBoulderName(entry.getDescription().getValue());
                    String grade = parsedData[1];
                    String routeName = parsedData[0];
                    Date sendDate = Date.from(entry.getPublishedDate().toInstant());
                    int timesSent = 1;

                    // Check for duplicates or handle duplicates appropriately
                    Tick existingTick = tickDao.findByBoulderNameAndGradeAndRouteNameAndSendDate(boulderName, grade, routeName, sendDate);
                    if (existingTick != null) {
                        // Existing entry, update the timesSent
                        existingTick.setTimesSent(existingTick.getTimesSent() + 1);
                        tickDao.save(existingTick);
                    } else {
                        // New entry
                        Tick tick = new Tick()
                                .withBoulderName(boulderName)
                                .withGrade(grade)
                                .withRouteName(routeName)
                                .withSendDate(sendDate)
                                .withTimesSent(timesSent);
                        tickDao.save(tick);
                    }
                } else {
                    logger.info("Invalid input string");
                }
            }
        } catch (IOException | FeedException e) {
            // Log or handle the exception appropriately
            throw new RuntimeException(e);
        }
    }


    private static String[] parseString(String input) {
        // Define a regex pattern to capture the name and code
        Pattern pattern = Pattern.compile("Tick:\\s(.*?)\\s\\((.*?)\\)");

        // Create a matcher with the input string
        Matcher matcher = pattern.matcher(input);

        // Check if the pattern matches the input
        if (matcher.find()) {
            // Extract the captured groups
            String name = matcher.group(1);
            String code = matcher.group(2);

            // Return the parsed data
            return new String[]{name, code};
        } else {
            // Return null if no match is found
            return null;
        }
    }

    public static String parseBoulderName(String description) {
        // Define a regex pattern to match the last <a> tag
        Pattern pattern = Pattern.compile("<a[^>]*>(.*?)</a>");

        // Create a matcher with the input description
        Matcher matcher = pattern.matcher(description);

        // Find the last match
        String lastATagContent = null;
        while (matcher.find()) {
            lastATagContent = matcher.group(1);
        }

        return lastATagContent;
    }

}
