package com.hive.ycbm.services.impl;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.exception.IDNotFoundException;
import com.hive.ycbm.exceptions.EmailNotFoundException;
import com.hive.ycbm.exceptions.UsernameNotFoundException;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.User;
import com.hive.ycbm.repositories.BookingPageRepository;
import com.hive.ycbm.repositories.UserRepository;
import com.hive.ycbm.services.BookingPageService;
import org.springframework.beans.factory.annotation.Autowired;
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
                .orElseThrow(() -> new IDNotFoundException("Booking page not found with id: " + pageId));
        return BookingPageDto.builder()
                .pageId(bookingPage.getPageId())
                .title(bookingPage.getTitle())
                .bookingLink(bookingPage.getBookingLink())
                .build();
    }
    @Override
    public void saveBookingPage(BookingPage bookingPage, String email)  {
        User user = userRepository.findByMainEmail(email).orElseThrow(() -> new EmailNotFoundException("Email not found"));
        bookingPage.setUser(user);
        this.bookingPageRepository.save(bookingPage);
    }

    @Override
    public void updateBookingPage(BookingPageDto bookingPageDto) {
        BookingPage updateBookingPage = bookingPageRepository.findById(bookingPageDto.getPageId()).orElseThrow(() -> new IDNotFoundException("Booking page not found with id: " + bookingPageDto.getPageId()));
        updateBookingPage.setTitle(bookingPageDto.getTitle());
        updateBookingPage.setBookingLink(bookingPageDto.getBookingLink());
        bookingPageRepository.save(updateBookingPage);
    }
    @Override
    public void deleteBookingPage(Long pageId) {
        BookingPage bookingPage = bookingPageRepository.findById(pageId)
                .orElseThrow(() -> new IDNotFoundException("Booking page not found with id: " + pageId));
        bookingPageRepository.delete(bookingPage);
    }
}
