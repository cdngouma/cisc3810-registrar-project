package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;

public interface ClassRepository extends JpaRepository <ClassEntity, Integer> {
    @Query(value = "SELECT 1 FROM classes c\n" +
            "WHERE ?1 = c.period_id\n" +
            // check for overlapping time
            "AND (?2 >= c.start_time AND ?2 <= c.end_time " +
            "OR c.end_time >= ?3 AND c.end_time <= ?2)\n" +
            // check for overlapping days
            "AND REGEXP_LIKE(c.meeting_days, ?4) <> 0\n" +
            // check for conflicting instructor or room
            "AND (?5 = c.instructor_id OR ?6 = c.room);", nativeQuery = true)
    Long findConflictWithClass(int periodId, Time endTime, Time startTime, String meetingDaysRegex, long instrId, String room);
}
