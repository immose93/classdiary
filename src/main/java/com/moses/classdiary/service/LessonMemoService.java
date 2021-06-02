package com.moses.classdiary.service;

import com.moses.classdiary.dto.lessonmemo.LessonMemoDateDto;
import com.moses.classdiary.dto.lessonmemo.LessonMemoDto;
import com.moses.classdiary.dto.lessonmemo.LessonMemoUpdateDto;
import com.moses.classdiary.entity.Lesson;
import com.moses.classdiary.entity.LessonMemo;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.repository.LessonMemoRepository;
import com.moses.classdiary.repository.LessonRepository;
import com.moses.classdiary.repository.MemberRepository;
import com.moses.classdiary.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonMemoService {
    private final LessonMemoRepository lessonMemoRepository;
    private final MemberRepository memberRepository;
    private final LessonRepository lessonRepository;
    private static final int MIN_PERIOD = 1;
    private static final int MAX_PERIOD = 6;

    /**
     * 수업 기록 조회하는 메소드
     * @param lessonMemoDateDto - 조회하려는 날짜 DTO
     * @return 해당 날짜의 수업 기록 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<LessonMemoDto> getLessonMemos(LessonMemoDateDto lessonMemoDateDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<LessonMemo> lessonMemoList = lessonMemoRepository.findAllByMemberAndDateOrderByPeriod(member, lessonMemoDateDto.getDate());

        // 처음 조회하는 날짜인 경우
        if (lessonMemoList.isEmpty()) {
            int day = lessonMemoDateDto.getDate().getDayOfWeek().get(ChronoField.DAY_OF_WEEK);
            List<Lesson> lessons = lessonRepository.findAllByMemberAndTypeAndDayOrderByPeriod(member, "기초", day);
            // 수업 기록 생성
            for (int i = MIN_PERIOD, idx = 0; i <= MAX_PERIOD; i++) {
                if (lessons.get(idx).getPeriod() != i) {
                    continue;
                }
                lessonMemoList.add(new LessonMemo(
                        member,
                        lessonMemoDateDto.getDate(),
                        lessons.get(idx).getSubjectTitle(),
                        day,
                        i,
                        ""
                ));
                idx++;
            }
        }
        return lessonMemoList.stream()
                .map(LessonMemoDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public LessonMemoUpdateDto updateLessonMemos(LessonMemoUpdateDto lessonMemoUpdateDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<LessonMemo> lessonMemoList = lessonMemoRepository.findAllByMemberAndDateOrderByPeriod(member, lessonMemoUpdateDto.getDate());

        // DB에 해당 날짜의 수업 기록이 없었을 경우 (새로 기록하여 저장하는 경우)
        if (lessonMemoList.isEmpty()) {
            int day = lessonMemoUpdateDto.getDate().getDayOfWeek().get(ChronoField.DAY_OF_WEEK);
            List<Lesson> lessons = lessonRepository.findAllByMemberAndTypeAndDayOrderByPeriod(member, "기초", day);
            // 수업 기록 생성
            for (int i = MIN_PERIOD, idx = 0; i <= MAX_PERIOD; i++) {
                if (lessons.get(idx).getPeriod() != i) {
                    continue;
                }
                lessonMemoList.add(new LessonMemo(
                        member,
                        lessonMemoUpdateDto.getDate(),
                        lessons.get(idx).getSubjectTitle(),
                        day,
                        i,
                        ""
                ));
                idx++;
            }
            // DB에 추가
            lessonMemoRepository.saveAll(lessonMemoList);
        }
        else {  // 해당 날짜의 기록을 수정하는 경우
            int idx = 0;
            for (LessonMemoDto lessonMemoDto : lessonMemoUpdateDto.getLessonMemoDtoList()) {
                lessonMemoList.get(idx++).update(lessonMemoDto.getContent());
            }
        }

        return lessonMemoUpdateDto;
    }
}
