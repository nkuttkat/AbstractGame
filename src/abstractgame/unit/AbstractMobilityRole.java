/* 
 * Copyright (C) 2018 Nils Kuttkat <nkuttkat@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package abstractgame.unit;

/**
 * The Class AbstractMobilityRole.
 *
 * @author Nils Kuttkat
 */
public abstract class AbstractMobilityRole {

    /**
     * The instance.
     */
    protected static AbstractMobilityRole INSTANCE;

    /**
     * Instantiates a new abstract mobility role.
     */
    protected AbstractMobilityRole() {
    }

    ;

	/**
	 * Checks if is immobile.
	 * 
	 * @return true, if is immobile
	 */
	public abstract boolean isImmobile();

    /**
     * Checks if is mobile.
     *
     * @return true, if is mobile
     */
    public abstract boolean isMobile();
}
