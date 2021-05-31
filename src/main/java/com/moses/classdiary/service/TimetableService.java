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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimetableService {
    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;
    private static final int MIN_PERIOD = 0;
    private static final int MAX_PERIOD = 5;
    private static final int MONDAY = 0;
    private static final int FRIDAY = 4;

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
        List<Lesson> addList = new ArrayList<>();
        List<Lesson> deleteList = new ArrayList<>();    // 삭제될 수업 목록

        // timetableDto 를 요일과 교시 순으로 체크
        for (int i = MONDAY; i <= FRIDAY; i++){
            for(int j = MIN_PERIOD; j <= MAX_PERIOD; j++) {
                String subjectTitle = timetableDto.getTimetable()[j][i];
                boolean isContained = false;
                // 기존 DB에 있던 시간표와 비교
                for (Lesson lesson : timetable) {
                    // 요일과 교시가 일치하면
                    if (lesson.getDay() == i && lesson.getPeriod() == j) {
                        isContained = true;
                        // 해당 시간에 수업이 없도록 수정되는 경우
                        if (subjectTitle.equals("")) {
                            deleteList.add(lesson); // 삭제 목록에 추가
                            break;
                        }
                        // 해당 시간에 다른 수업으로 수정되는 경우 (변경 감지로 DB에 적용)
                        lesson.setSubjectTitle(subjectTitle);
                        break;
                    }
                }
                // 새로 추가되는 수업이면
                if (!isContained && !subjectTitle.equals("")) {
                    addList.add(new Lesson(member, timetableDto.getType(), i, j, subjectTitle));
                }
            }
        }

        // 새로 추가되는 수업 추가
        lessonRepository.saveAll(addList);
        // 삭제될 수업 삭제
        lessonRepository.deleteAll(deleteList);
        return timetableDto;
    }
}
