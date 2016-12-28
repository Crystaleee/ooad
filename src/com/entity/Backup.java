package com.entity;
// Generated 2016-12-28 20:12:31 by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Backup generated by hbm2java
 */
@Entity
@Table(name = "backup", catalog = "ooad")
public class Backup implements java.io.Serializable {

	private Integer backupId;
	private String backupName;
	private Date datePurchased;
	private Date dateDiscarded;
	private byte isInstalled;
	private Set<RecordBackup> recordBackups = new HashSet<RecordBackup>(0);

	public Backup() {
	}

	public Backup(String backupName, Date datePurchased, byte isInstalled) {
		this.backupName = backupName;
		this.datePurchased = datePurchased;
		this.isInstalled = isInstalled;
	}

	public Backup(String backupName, Date datePurchased, Date dateDiscarded, byte isInstalled,
			Set<RecordBackup> recordBackups) {
		this.backupName = backupName;
		this.datePurchased = datePurchased;
		this.dateDiscarded = dateDiscarded;
		this.isInstalled = isInstalled;
		this.recordBackups = recordBackups;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "backupID", unique = true, nullable = false)
	public Integer getBackupId() {
		return this.backupId;
	}

	public void setBackupId(Integer backupId) {
		this.backupId = backupId;
	}

	@Column(name = "backupName", nullable = false)
	public String getBackupName() {
		return this.backupName;
	}

	public void setBackupName(String backupName) {
		this.backupName = backupName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "datePurchased", nullable = false, length = 10)
	public Date getDatePurchased() {
		return this.datePurchased;
	}

	public void setDatePurchased(Date datePurchased) {
		this.datePurchased = datePurchased;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dateDiscarded", length = 10)
	public Date getDateDiscarded() {
		return this.dateDiscarded;
	}

	public void setDateDiscarded(Date dateDiscarded) {
		this.dateDiscarded = dateDiscarded;
	}

	@Column(name = "isInstalled", nullable = false)
	public byte getIsInstalled() {
		return this.isInstalled;
	}

	public void setIsInstalled(byte isInstalled) {
		this.isInstalled = isInstalled;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "backup")
	public Set<RecordBackup> getRecordBackups() {
		return this.recordBackups;
	}

	public void setRecordBackups(Set<RecordBackup> recordBackups) {
		this.recordBackups = recordBackups;
	}

}
