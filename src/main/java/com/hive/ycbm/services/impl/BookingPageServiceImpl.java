package com.hive.ycbm.services.impl;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.exceptions.CustomException;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.User;
import com.hive.ycbm.repositories.BookingPageRepository;
import com.hive.ycbm.repositories.UserRepository;
import com.hive.ycbm.services.BookingPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingPageServiceImpl implements BookingPageService {
    @Autowired
    private BookingPageRepository bookingPageRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<BookingPageDto> getBookingPagesByUser(String email) {
        User user = userRepository.findByMainEmail(email).orElseThrow(null);
        List<BookingPage> bookingPages = bookingPageRepository.findByUser(user);
        return bookingPages.stream().map(bookingPage -> BookingPageDto.builder()
                .pageId(bookingPage.getPageId())
                .title(bookingPage.getTitle())
                .bookingLink(bookingPage.getBookingLink())
                .build()).collect(Collectors.toList());
    }

    @Override
    public BookingPageDto findById(Long pageId) {
        BookingPage bookingPage = bookingPageRepository.findById(pageId)
                .orElseThrow(() -> new CustomException("Booking page not found with id: " + pageId,
                        HttpStatus.NOT_FOUND));
        return BookingPageDto.builder()
                .pageId(bookingPage.getPageId())
                .title(bookingPage.getTitle())
                .bookingLink(bookingPage.getBookingLink())
                .build();
    }

    @Override
    public void saveBookingPage(BookingPage bookingPage, String email) {
        User user = userRepository.findByMainEmail(email).orElseThrow(() -> new CustomException("Email not found",
                HttpStatus.NOT_FOUND));
        bookingPage.setUser(user);
        this.bookingPageRepository.save(bookingPage);
    }

    @Override
    public void updateBookingPage(BookingPageDto bookingPageDto) {
        BookingPage updateBookingPage = bookingPageRepository.findById(bookingPageDto.getPageId())
                .orElseThrow(() -> new CustomException("Booking page not found with id: " + bookingPageDto.getPageId(),
                        HttpStatus.NOT_FOUND));
        updateBookingPage.setTitle(bookingPageDto.getTitle());
        updateBookingPage.setBookingLink(bookingPageDto.getBookingLink());
        bookingPageRepository.save(updateBookingPage);
    }

    @Override
    public void deleteBookingPage(Long pageId) {
        BookingPage bookingPage = bookingPageRepository.findById(pageId)
                .orElseThrow(() -> new CustomException("Booking page not found with id: " + pageId, HttpStatus.NOT_FOUND));
        bookingPageRepository.delete(bookingPage);
    }
}
