package com.bp.alps.core.dao;

import com.bp.alps.core.model.StoreVoucherDescriptionModel;

import java.util.List;


public interface StoreVoucherDao
{
	List<StoreVoucherDescriptionModel> getVoucherList();

	List<StoreVoucherDescriptionModel> getVoucherBycodes(List<String> code);
}
