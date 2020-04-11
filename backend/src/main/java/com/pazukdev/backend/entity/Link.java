package com.pazukdev.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Entity
@Table(name = "link")
public class Link extends AbstractEntity {

    private String type;
    private String url;
    @Column(name = "country_code")
    private String countryCode;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        final Link link = (Link) o;
//        if (this.getId() != null && link.getId() != null) {
//            return this.getId().equals(link.getId());
//        }
//        return Objects.equals(type, link.type)
//                && Objects.equals(countryCode, link.countryCode);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), type, countryCode);
//    }
//
//    public void addTo(final Set<Link> set) {
//        addTo(set, false);
//    }
//
//    public void addTo(final Set<Link> set, final boolean consideringUrl) {
//        if (this.getId() != null) {
//            set.add(this);
//            return;
//        }
//        Link toRemove = null;
//        Link toAdd = null;
//        for (final Link link : set) {
//            final Long id = link.getId();
//            link.setId(null);
//            if (this.getUrl().equals("1")) {
//                int i = 0;
//            }
//            boolean a = link.getUrl().equals(this.getUrl());
//            boolean b = !consideringUrl || link.getUrl().equals(this.getUrl());
//            if (link.equals(this) && (!consideringUrl || link.getUrl().equals(this.getUrl()))) {
//                link.setId(id);
//                this.setId(id);
//                toRemove = link;
//                toAdd = this;
//                break;
//            }
//            link.setId(id);
//        }
//        if (toRemove != null) {
//            set.remove(toRemove);
//            set.add(toAdd);
//        } else {
//            set.add(this);
//        }
//    }

}
