package info.coelho.etl.service.extraction;

import info.coelho.etl.service.ActivityProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ActivityParserFactoryTest {

    @Test
    void when_contentType_is_unknown_should_throw_exception() {

        List<ActivityParser> parsers = Arrays.asList(new ActivityParserXML(), new ActivityParserJson());
        ActivityParserFactory factory = new ActivityParserFactory(parsers);

        Assertions.assertThatThrownBy(() -> factory.getInstance("application/text"))
                .isInstanceOf(ActivityProcessingException.class);

    }

}