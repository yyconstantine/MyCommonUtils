public class SimpleSearch {

    /**
     * 先使用LocalDate比较时间间隔
     * 再将两个不同服务查询的list,根据某一参数进行拼接
     * @param customCardOrderQueryDTO
     * @return
     */
    public Result<List<CustomCardOrderVO>> listCustomCardOrder(CustomCardOrderQueryDTO customCardOrderQueryDTO) {
        LocalDate startDate = LocalDate.parse(customCardOrderQueryDTO.getStartDate());
        LocalDate endDate = LocalDate.parse(customCardOrderQueryDTO.getEndDate());
        if (endDate.compareTo(startDate) > this.orderSearchInterval) {
            log.error("查询定制卡订单时间不能超过7天");
            return Result.error(ErrorEnum.SEARCH_INTERVAL_ILLEGAL);
        }
        List<OrderDO> orderList;
        List<MerStoreDO> storeList;
        List<CustomCardOrderVO> customOrderList;

        try {
            orderList = this.payCenterService.getOrderListByOrderType(customCardOrderQueryDTO);

            StoreQueryDTO storeQueryDTO = StoreQueryDTO.builder()
                    .storeIds(orderList.stream()
                            .map(OrderDO::getStoreId)
                            .distinct()
                            .collect(Collectors.toList()))
                    .build();
            storeList = this.merchantServiceFacade.getStoreListById(storeQueryDTO).getData();

            customOrderList = orderList.stream()
                    .flatMap(i -> storeList.stream()
                            .filter(j -> i.getStoreId().equals(j.getId()))
                            .map(j -> CustomCardOrderVO.builder()
                                    .orderId(i.getOrderId())
                                    .orderNo(i.getOrderNo())
                                    .storeName(j.getStoreName())
                                    .paidAmount(i.getPaidAmount())
                                    .orderDate(i.getOrderDate())
                                    .build()))
                    .collect(Collectors.toList());

            return Result.success(SuccessEnum.GLOBAL_SEARCH_SUCCESS, customOrderList);
        } catch (Exception e) {
            log.error("查询定制订单信息出错: ", e);
            return Result.error(ErrorEnum.GLOBAL_SEARCH_ERROR);
        }
    }

}