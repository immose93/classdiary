package com.moses.classdiary.service;

import com.moses.classdiary.dto.timetable.TimetableDto;
import com.moses.classdiary.entity.Lesson;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.repository.LessonRepository;
import com.moses.classdiary.repository.MemberRepository;
import com.moses.classdiary.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimetableService {
    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;
    private static final int MIN_PERIOD = 1;
    private static final int MAX_PERIOD = 6;
    private static final int MONDAY = 1;
    private static final int FRIDAY = 5;

    @Transactional(readOnly = true)
    public TimetableDto getTimetable(String timetableType) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<Lesson> timetable = lessonRepository.findAllByMemberAndTypeOrderByDayAscPeriodAsc(member, timetableType);
        return new TimetableDto(timetable);
    }

    @Transactional
    public TimetableDto updateTimetable(TimetableDto timetableDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<Lesson> timetable = lessonRepository.findAllByMemberAndTypeOrderByDayAscPeriodAsc(member, timetableDto.getType());

        for (int i = MIN_PERIOD; i <= MAX_PERIOD; i++){
            for(int j = MONDAY; j <= FRIDAY; j++) {
                String subjectTitle = timetableDto.getTimetable()[i][j];
               if (!subjectTitle.equals("")){
                   Lesson lesson = new Lesson(member, timetableDto.getType(), j, i, subjectTitle);
               }
            }
        }
        return timetableDto;
    }
}
