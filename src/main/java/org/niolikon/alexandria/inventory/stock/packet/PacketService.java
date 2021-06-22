package org.niolikon.alexandria.inventory.stock.packet;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.niolikon.alexandria.inventory.catalog.commons.ProductRepository;
import org.niolikon.alexandria.inventory.stock.packet.converter.PacketToPacketViewConverter;
import org.niolikon.alexandria.inventory.stock.packet.dto.PacketRequest;
import org.niolikon.alexandria.inventory.stock.packet.dto.PacketView;
import org.niolikon.alexandria.inventory.stock.packet.entities.Packet;
import org.niolikon.alexandria.inventory.system.MessageProvider;
import org.niolikon.alexandria.inventory.system.exceptions.EntityDuplicationException;
import org.niolikon.alexandria.inventory.system.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacketService {
    
    private final PacketRepository packetRepo;
    private final ProductRepository productRepo;
    private final PacketToPacketViewConverter packetConverter;
    private final MessageProvider messageProvider;
    
    public PacketService(PacketRepository packetRepo,
    		ProductRepository productRepo,
            PacketToPacketViewConverter packetConverter,
            MessageProvider messageUtil) {
        this.packetRepo = packetRepo;
        this.productRepo = productRepo;
        this.packetConverter = packetConverter;
        this.messageProvider = messageUtil;
    }
    
    public Packet findPacketOrThrow(Long id) {
        return packetRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageProvider.getMessage("packet.NotFound", id)));
    }
    
    public PacketView getPacket(Long id) {
        Packet packet = findPacketOrThrow(id);
        return packetConverter.convert(packet);
    }

    public Page<PacketView> findAllPackets(Pageable pageable) {
        Page<Packet> packets = packetRepo.findAll(pageable);
        List<PacketView> packetViews = new ArrayList<>();
        packets.forEach(packet -> {
            PacketView packetView = packetConverter.convert(packet);
            packetViews.add(packetView);
        });
        return new PageImpl<>(packetViews, pageable, packets.getTotalElements());
    }
    
    public PacketView create(PacketRequest req) {
        Packet packet = new Packet();
        this.fetchFromRequest(packet, req);

        try {
            Packet packetSaved = packetRepo.save(packet);
            return packetConverter.convert(packetSaved);
        } catch (DataIntegrityViolationException e) {
            throw new EntityDuplicationException(messageProvider.getMessage("packet.Duplication", packet.getBarcode()));
        }
    }
    
    @Transactional
    public void delete(Long id) {
        try {
            packetRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageProvider.getMessage("packet.NotFound", id));
        }
    }
    
    public PacketView update(Packet packet, PacketRequest req) {
        Packet packetUpdated = this.fetchFromRequest(packet, req);
        Packet packetSaved = packetRepo.save(packetUpdated);
        return packetConverter.convert(packetSaved);
    }
    
    private Packet fetchFromRequest(Packet packet, PacketRequest req) {
        packet.setLocation(req.getLocation());
        packet.setSize(req.getSize());
        packet.setWeight(req.getWeight());
        packet.setBarcode(req.getBarcode());
        packet.setOrderId(req.getOrderId());
        packet.setShipmentId(req.getShipmentId());
        packet.setProduct(productRepo.getById(req.getProductId()));
        return packet;
    }
}
