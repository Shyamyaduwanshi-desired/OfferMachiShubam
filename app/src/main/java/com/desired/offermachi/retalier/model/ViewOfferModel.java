package com.desired.offermachi.retalier.model;

public class ViewOfferModel {
    private String id,Offerid,Offername,OfferCategory,OfferSubCategory,Offertype,Offertypename,Offervalue,OfferDetail,Offerstartdate,
            Offerenddate,Offertime,Offerdescription,Coupon_code,Posted_by,Offerstatus,Offerbrandname,OfferImage,Offerqrcodeimage;

    public ViewOfferModel(String id,String offerid,String offername,String offerCategory,String offerSubCategory,
                          String offertype,String offertypename,String offervalue,String offerDetail,String offerstartdate,String offerenddate,String offertime,
                          String offerdescription,String coupon_code,String posted_by,String offerstatus,String offerbrandname,String offerImage,String offerqrcodeimage) {
        this.id = id;
        this.Offerid = offerid;
        this.Offername = offername;
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
        this.OfferImage=offerImage;
        this.Offerqrcodeimage=offerqrcodeimage;
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

    public String getOffervalue() {
        return Offervalue;
    }

    public String getOfferDetail() {
        return OfferDetail;
    }

    public String getOfferImage() {
        return OfferImage;
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

    public String getOfferqrcodeimage() {
        return Offerqrcodeimage;
    }

    public String getOffertypename() {
        return Offertypename;
    }

    public String getOfferbrandname() {
        return Offerbrandname;
    }
}
