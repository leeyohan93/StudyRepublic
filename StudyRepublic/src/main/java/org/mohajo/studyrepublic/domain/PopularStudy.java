package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.ToString;

/**
 * @author	이미연
 * @since	2019. 2. 8.
 * @version	
 */
@Data
@ToString(exclude = "review")
@Entity
@Table(name = "popular_study"/*, schema = "StudyRepublic"*/)
@DiscriminatorValue("POPULARSTUDY")
public class PopularStudy extends Study implements Serializable {

}
