package org.mohajo.studyrepublic.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "member_interest", schema = "StudyRepublic")
public class MemberInterest {

}
