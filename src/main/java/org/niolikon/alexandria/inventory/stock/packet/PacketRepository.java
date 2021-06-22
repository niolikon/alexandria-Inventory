package org.niolikon.alexandria.inventory.stock.packet;

import org.niolikon.alexandria.inventory.stock.packet.entities.Packet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacketRepository extends JpaRepository<Packet, Long> {
}
