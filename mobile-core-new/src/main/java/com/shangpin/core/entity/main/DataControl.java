/**
 * 
 */
package com.shangpin.core.entity.main;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.shangpin.core.entity.Idable;
import com.google.common.collect.Lists;

/**
 * @author sundful
 *
 */
@Entity
@Table(name="security_data_control")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.shangpin.core.entity.main.DataControl")
public class DataControl implements Idable<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Length(max=32)
	@Column(nullable=false, length=32, unique=true, updatable=false)
	private String name;
	
	@Length(max=255)
	@Column(length=255)
	private String description;
	
	@Length(max=255)
	@Column(length=255)
	private String control;
	
	@OneToMany(mappedBy="dataControl", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	private List<RolePermissionDataControl> rolePermissionDataControls = Lists.newArrayList();
	
	/* (non-Javadoc)
	 * @see com.shangpin.manager.entity.Idable#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see com.shangpin.manager.entity.Idable#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the control
	 */
	public String getControl() {
		return control;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param control the control to set
	 */
	public void setControl(String control) {
		this.control = control;
	}
	
	/**
	 * @return the rolePermissionDataControls
	 */
	public List<RolePermissionDataControl> getRolePermissionDataControls() {
		return rolePermissionDataControls;
	}

	/**
	 * @param rolePermissionDataControls the rolePermissionDataControls to set
	 */
	public void setRolePermissionDataControls(
			List<RolePermissionDataControl> rolePermissionDataControls) {
		this.rolePermissionDataControls = rolePermissionDataControls;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
