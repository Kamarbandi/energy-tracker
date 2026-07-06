package com.azad.device_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "device")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Device {

}
