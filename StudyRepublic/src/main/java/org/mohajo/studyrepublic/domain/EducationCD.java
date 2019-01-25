package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "education_cd", schema = "StudyRepublic")
public class EducationCD {

@Id
@Column(name = "EDUCATION_CODE")
private String educationCode;

@Column(name = "CODE_VALUE_ENGLISH")
private String codeValueEnglish;

@Column(name = "CODE_VALUE_KOREAN")
private String codeValueKorean;

@Column(name = "SEQUENCENUM")
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int sequenceNum;

}
