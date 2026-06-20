package com.example.spendora.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantId", type = String.class))
@Filter(name = "tenantFilter", condition = "app_user_id = :tenantId")
public abstract class TenantAware extends Auditable {
    // The appUser FK is defined in each subclass to allow flexibility
    // (e.g., OneToOne vs ManyToOne). The filter condition references
    // the physical column name "app_user_id" which all subclasses must use.
}
