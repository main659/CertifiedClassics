package ibm.java.academy.cerfiticationsapp.request;


import javax.validation.constraints.NotNull;

public class VoucherUpdateRequest {
    @NotNull
	private Long voucherId;

	@NotNull
	private Long userId;

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
