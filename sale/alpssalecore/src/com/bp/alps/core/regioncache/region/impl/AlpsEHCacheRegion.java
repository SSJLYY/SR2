package com.bp.alps.core.regioncache.region.impl;

import de.hybris.platform.regioncache.CacheValueLoader;
import de.hybris.platform.regioncache.key.CacheKey;
import de.hybris.platform.regioncache.region.impl.EHCacheRegion;
import org.aspectj.lang.ProceedingJoinPoint;

import org.apache.log4j.Logger;


public class AlpsEHCacheRegion
{
	private static final Logger log = Logger.getLogger(AlpsEHCacheRegion.class);

	public AlpsEHCacheRegion(){
		System.out.println(" AlpsEHCacheRegion has been created ");
	}

	public Object getWithLoaderListen(final ProceedingJoinPoint point) throws Throwable
	{
		Object[] args = point.getArgs();
		if(point.getTarget() instanceof EHCacheRegion)
		{
			if(args.length>1)
			{
				CacheKey cacheKey = (CacheKey) args[0];
				CacheValueLoader loader = (CacheValueLoader) args[1];
				EHCacheRegion ehCacheRegion = (EHCacheRegion) point.getTarget();
				if (!ehCacheRegion.containsKey(cacheKey))
				{
					Object value = loader.load(cacheKey);
					log.info(ehCacheRegion.getName() + " hash key: " + cacheKey.hashCode() + " cache saves the following parameter:");
					log.info(value.toString());
				}
			}
		}
		return point.proceed(args);
	}

	public Object invalidateKeyListen(final ProceedingJoinPoint point) throws Throwable{
		Object[] args = point.getArgs();
		if(point.getTarget() instanceof EHCacheRegion){
			CacheKey cacheKey = (CacheKey) args[0];
			EHCacheRegion ehCacheRegion = (EHCacheRegion) point.getTarget();
			log.info(ehCacheRegion.getName() + " hash key: "+cacheKey.hashCode()+" cache has been invalidate");
		}
		return point.proceed(args);
	}
}
