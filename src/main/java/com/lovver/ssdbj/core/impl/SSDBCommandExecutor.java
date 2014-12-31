package com.lovver.ssdbj.core.impl;

import java.util.List;

import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.core.CommandExecutor;
import com.lovver.ssdbj.exception.SSDBException;

public class SSDBCommandExecutor implements CommandExecutor {

	@Override
	public BaseResultSet execute(String cmd,List<byte[]> params) throws SSDBException {
		return null;
	}
}
