package ca.uqam.mgl7230.tp1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {

    @Test
    void instantiateApplicationClass() {
        // When
        Application application = new Application();

        // Then
        assertThat(application).isNotNull();
    }

    @Test
    void mainTest() throws IOException {
        // When
        InputStream in = new ByteArrayInputStream("UQAM005\np1\nn1\n1\nfirst\nyes\np2\nn2\n2\neconomy\nno".getBytes());
        System.setIn(in);
        Application.main(new String[]{});

    }

}