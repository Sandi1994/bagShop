package lk.sanchana_bag_shop.asset.discount_ratio.service;


import lk.sanchana_bag_shop.asset.common_asset.model.enums.LiveDead;
import lk.sanchana_bag_shop.asset.discount_ratio.dao.DiscountRatioDao;
import lk.sanchana_bag_shop.asset.discount_ratio.entity.DiscountRatio;
import lk.sanchana_bag_shop.asset.discount_ratio.entity.enums.DiscountRatioStatus;
import lk.sanchana_bag_shop.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountRatioService implements AbstractService< DiscountRatio, Integer > {
private final DiscountRatioDao discountRatioDao;

    public DiscountRatioService(DiscountRatioDao discountRatioDao) {
        this.discountRatioDao = discountRatioDao;
    }

    public List< DiscountRatio > findAll() {
        return discountRatioDao.findAll().stream()
            .filter(x -> LiveDead.ACTIVE.equals(x.getLiveDead()))
            .collect(Collectors.toList());
    }

    public DiscountRatio findById(Integer id) {
        return discountRatioDao.getOne(id);
    }

    public DiscountRatio persist(DiscountRatio discountRatio) {
        if ( discountRatio.getId() == null ){
            discountRatio.setLiveDead(LiveDead.ACTIVE);
            discountRatio.setDiscountRatioStatus(DiscountRatioStatus.ACTIVE);
        }
        return discountRatioDao.save(discountRatio);
    }

    public boolean delete(Integer id) {
        DiscountRatio discountRatio =  discountRatioDao.getOne(id);
        discountRatio.setLiveDead(LiveDead.STOP);
        discountRatioDao.save(discountRatio);
        return false;
    }

    public List< DiscountRatio > search(DiscountRatio discountRatio) {
        return null;
    }
}
