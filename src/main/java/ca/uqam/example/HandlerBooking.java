package ca.uqam.example;

import ca.uqam.example.service.BookingStatus;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class HandlerBooking implements RequestHandler<Booking, BookingStatus> {
    @Override
    public BookingStatus handleRequest(Booking booking, Context context) {
        return null;
    }
}
