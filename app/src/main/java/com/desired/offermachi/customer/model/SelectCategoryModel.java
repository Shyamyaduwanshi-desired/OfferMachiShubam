package com.desired.offermachi.customer.model;

public class SelectCategoryModel {
    private String id,Offerid,Offername,OfferCategory,offer_title_slug,OfferSubCategory,Offertype,Offertypename,Offervalue,OfferDetail,Offerstartdate,
            Offerenddate,Offertime,Offerdescription,Coupon_code,Posted_by,Offerstatus,Offerbrandname,Offerfav,OfferImage,Offerqrcodeimage,OfferCouponCodeStatus,storeLogo ;

    public String getOffer_title_slug() {
        return offer_title_slug;
    }

    public void setOffer_title_slug(String offer_title_slug) {
        this.offer_title_slug = offer_title_slug;
    }

    public SelectCategoryModel(String id, String offerid, String offername, String offer_title_slug, String offerCategory, String offerSubCategory,
                               String offertype, String offertypename, String offervalue, String offerDetail, String offerstartdate, String offerenddate, String offertime,
                               String offerdescription, String coupon_code, String posted_by, String offerstatus, String offerbrandname, String offerfav, String offerImage, String offerqrcodeimage, String offerCouponCodeStatus, String storeLogo) {
        this.id = id;
        this.Offerid = offerid;
        this.Offername = offername;
        this.offer_title_slug = offer_title_slug;
        this.OfferCategory=offerCategory;
        this.OfferSubCategory=offerSubCategory;
        this.Offertype=offertype;
        this.Offertypename=offertypename;
        this.Offervalue=offervalue;
        this.OfferDetail=offerDetail;
        this.Offerstartdate=offerstartdate;
        this.Offerenddate=offerenddate;
        this.Offertime=offertime;
        this.Offerdescription=offerdescription;
        this.Coupon_code=coupon_code;
        this.Posted_by=posted_by;
        this.Offerstatus=offerstatus;
        this.Offerbrandname=offerbrandname;
        this.Offerfav=offerfav;
        this.OfferImage=offerImage;
        this.Offerqrcodeimage=offerqrcodeimage;
        this.OfferCouponCodeStatus=offerCouponCodeStatus;
        this.storeLogo=storeLogo;
    }

    public String getId() {
        return id;
    }

    public String getOfferid() {
        return Offerid;
    }

    public String getOffername() {
        return Offername;
    }

    public String getOfferCategory() {
        return OfferCategory;
    }

    public String getOfferSubCategory() {
        return OfferSubCategory;
    }

    public String getOffertype() {
        return Offertype;
    }

    public String getOffertypename() {
        return Offertypename;
    }

    public String getOffervalue() {
        return Offervalue;
    }

    public String getOfferDetail() {
        return OfferDetail;
    }

    public String getOfferstartdate() {
        return Offerstartdate;
    }

    public String getOfferenddate() {
        return Offerenddate;
    }

    public String getOffertime() {
        return Offertime;
    }

    public String getOfferdescription() {
        return Offerdescription;
    }

    public String getCoupon_code() {
        return Coupon_code;
    }

    public String getPosted_by() {
        return Posted_by;
    }

    public String getOfferstatus() {
        return Offerstatus;
    }

    public String getOfferbrandname() {
        return Offerbrandname;
    }

    public String getOfferfav() {
        return Offerfav;
    }

    public String getOfferImage() {
        return OfferImage;
    }

    public String getOfferqrcodeimage() {
        return Offerqrcodeimage;
    }

    public String getOfferCouponCodeStatus() {
        return OfferCouponCodeStatus;
    }

    public String getStoreLogo() {
        return storeLogo;
    }
}
